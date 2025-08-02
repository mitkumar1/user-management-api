pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.0' // Configure this in Jenkins Global Tool Configuration
        jdk 'JDK-17'        // Configure this in Jenkins Global Tool Configuration
        nodejs 'NodeJS-18'  // Configure this in Jenkins Global Tool Configuration
    }
    
    environment {
        MAVEN_OPTS = '-Xmx1024m'
        SPRING_PROFILES_ACTIVE = 'test'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout scm
            }
        }
        
        stage('Validate') {
            steps {
                echo 'Validating Maven project structure...'
                sh 'mvn validate'
            }
        }
        
        stage('Compile Backend') {
            steps {
                echo 'Compiling Java source code...'
                sh 'mvn clean compile'
            }
        }
        
        stage('Test Backend') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    
                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/*', allowEmptyArchive: true
                }
            }
        }
        
        stage('Code Quality Analysis') {
            parallel {
                stage('SonarQube Analysis') {
                    when {
                        // Only run SonarQube on main/master branch
                        anyOf {
                            branch 'main'
                            branch 'master'
                        }
                    }
                    steps {
                        script {
                            // Configure SonarQube server in Jenkins
                            withSonarQubeEnv('SonarQube') {
                                sh 'mvn sonar:sonar'
                            }
                        }
                    }
                }
                
                stage('Dependency Check') {
                    steps {
                        echo 'Checking for security vulnerabilities...'
                        sh 'mvn org.owasp:dependency-check-maven:check'
                    }
                    post {
                        always {
                            archiveArtifacts artifacts: 'target/dependency-check-report.html', allowEmptyArchive: true
                        }
                    }
                }
            }
        }
        
        stage('Package Backend') {
            steps {
                echo 'Packaging Spring Boot application...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('frontend/user-management-ui') {
                    echo 'Installing Node.js dependencies...'
                    sh 'npm ci'
                    
                    echo 'Running frontend tests...'
                    sh 'npm run test -- --watch=false --browsers=ChromeHeadless'
                    
                    echo 'Building Angular application...'
                    sh 'npm run build --prod'
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'frontend/user-management-ui/dist/**', allowEmptyArchive: true
                }
            }
        }
        
        stage('Build Docker Images') {
            parallel {
                stage('Backend Docker Image') {
                    steps {
                        echo 'Building backend Docker image...'
                        script {
                            def backendImage = docker.build("user-management-api:${env.BUILD_NUMBER}")
                            
                            // Tag with latest for main/master branch
                            if (env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'master') {
                                backendImage.tag('latest')
                            }
                        }
                    }
                }
                
                stage('Frontend Docker Image') {
                    steps {
                        echo 'Building frontend Docker image...'
                        dir('frontend/user-management-ui') {
                            script {
                                def frontendImage = docker.build("user-management-ui:${env.BUILD_NUMBER}", "-f Dockerfile .")
                                
                                if (env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'master') {
                                    frontendImage.tag('latest')
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Integration Tests') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                echo 'Running integration tests...'
                sh 'docker-compose -f docker-compose.test.yml up --abort-on-container-exit'
            }
            post {
                always {
                    sh 'docker-compose -f docker-compose.test.yml down'
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Deploying to staging environment...'
                script {
                    // Deploy to staging
                    sh 'docker-compose -f docker-compose.staging.yml up -d'
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                }
            }
            steps {
                input message: 'Deploy to Production?', ok: 'Deploy'
                echo 'Deploying to production environment...'
                script {
                    // Deploy to production
                    sh 'docker-compose -f docker-compose.prod.yml up -d'
                }
            }
        }
    }
    
    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        
        success {
            echo 'Pipeline completed successfully!'
            // Send success notification
            emailext (
                subject: "✅ Build Success: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build completed successfully.\nBuild URL: ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
        
        failure {
            echo 'Pipeline failed!'
            // Send failure notification
            emailext (
                subject: "❌ Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build failed. Please check the logs.\nBuild URL: ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
        
        unstable {
            echo 'Pipeline completed with issues!'
            emailext (
                subject: "⚠️ Build Unstable: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build completed with warnings.\nBuild URL: ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}
