# Jenkins CI/CD Setup Guide for User Management API

## Prerequisites

### 1. Jenkins Server Setup
- Jenkins 2.400+ with Pipeline plugin
- Docker installed on Jenkins agent
- Git plugin installed

### 2. Required Jenkins Plugins
```bash
# Install these plugins in Jenkins
- Pipeline
- Docker Pipeline
- Git
- Maven Integration
- NodeJS
- SonarQube Scanner
- Email Extension
- Publish Test Results
- HTML Publisher
```

### 3. Global Tool Configuration
Navigate to `Manage Jenkins > Global Tool Configuration`:

#### Maven Configuration
- Name: `Maven-3.9.0`
- Install automatically: ✓
- Version: 3.9.0

#### JDK Configuration
- Name: `JDK-17`
- Install automatically: ✓
- Version: OpenJDK 17

#### NodeJS Configuration
- Name: `NodeJS-18`
- Install automatically: ✓
- Version: 18.x

## Jenkins Pipeline Setup

### 1. Create New Pipeline Job
1. Go to Jenkins Dashboard
2. Click "New Item"
3. Enter name: `user-management-api`
4. Select "Pipeline"
5. Click "OK"

### 2. Configure Pipeline
```groovy
Pipeline Definition: Pipeline script from SCM
SCM: Git
Repository URL: https://github.com/mitkumar1/user-management-api.git
Credentials: (your GitHub credentials)
Branch: */master
Script Path: Jenkinsfile
```

### 3. Configure Webhooks (Optional)
In your GitHub repository:
1. Go to Settings > Webhooks
2. Add webhook: `http://your-jenkins-server/github-webhook/`
3. Content type: `application/json`
4. Events: Just the push event

## Environment Configuration

### 1. Jenkins Credentials
Add these credentials in `Manage Jenkins > Manage Credentials`:

```bash
# Database Credentials
ID: db-credentials
Type: Username with password
Username: your_db_user
Password: your_db_password

# Docker Registry
ID: docker-registry
Type: Username with password
Username: your_docker_username
Password: your_docker_password

# SonarQube Token
ID: sonar-token
Type: Secret text
Secret: your_sonar_token

# Email Configuration
ID: email-credentials
Type: Username with password
Username: jenkins@company.com
Password: your_email_password
```

### 2. Global Environment Variables
In `Manage Jenkins > Configure System > Global Properties`:

```bash
DOCKER_REGISTRY=your-registry.com
SONAR_HOST_URL=http://sonarqube:9000
NOTIFICATION_EMAIL=team@company.com
```

## SonarQube Integration

### 1. SonarQube Server Configuration
1. Install SonarQube Scanner plugin
2. Go to `Manage Jenkins > Configure System`
3. Add SonarQube server:
   - Name: `SonarQube`
   - Server URL: `http://your-sonarqube:9000`
   - Authentication token: (use sonar-token credential)

### 2. Quality Gates
Configure quality gates in SonarQube:
- Coverage > 80%
- Duplicated lines < 3%
- Maintainability rating = A
- Reliability rating = A
- Security rating = A

## Email Notifications

### 1. Email Extension Configuration
In `Manage Jenkins > Configure System > Extended E-mail Notification`:

```bash
SMTP server: smtp.gmail.com
SMTP port: 587
Use SMTP Authentication: ✓
Username: jenkins@company.com
Password: (use email-credentials)
Use SSL: ✓
```

### 2. Default Recipients
```bash
Default Recipients: ${NOTIFICATION_EMAIL}
Default Subject: $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!
Default Content: Build URL: $BUILD_URL
```

## Docker Configuration

### 1. Docker Daemon
Ensure Docker is installed and Jenkins user has access:
```bash
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins
```

### 2. Docker Registry
Configure Docker registry credentials for pushing images.

## Branch Strategy

### Pipeline Behavior by Branch:
- **feature/***: Build + Test only
- **develop**: Build + Test + Deploy to Staging
- **master/main**: Full pipeline + Deploy to Production (with approval)

## Monitoring and Alerts

### 1. Build Notifications
- Success: Email to commit author
- Failure: Email to commit author + team
- Unstable: Email to commit author

### 2. Pipeline Metrics
Monitor these metrics:
- Build success rate
- Average build time
- Test coverage trends
- Code quality trends

## Security Best Practices

### 1. Credential Management
- Use Jenkins credentials store
- Rotate credentials regularly
- Use least privilege principle

### 2. Pipeline Security
- Scan for vulnerabilities
- Use signed Docker images
- Implement security gates

## Troubleshooting

### Common Issues:

#### 1. Maven Build Fails
```bash
# Check Java version
java -version

# Check Maven settings
mvn -version

# Clean and retry
mvn clean compile
```

#### 2. Docker Build Fails
```bash
# Check Docker daemon
docker info

# Check disk space
df -h

# Clean Docker cache
docker system prune
```

#### 3. Node.js Build Fails
```bash
# Check Node version
node --version

# Clear npm cache
npm cache clean --force

# Remove node_modules and reinstall
rm -rf node_modules && npm install
```

## Pipeline Stages Explained

1. **Checkout**: Get code from repository
2. **Validate**: Validate Maven project structure
3. **Compile Backend**: Compile Java code
4. **Test Backend**: Run unit tests
5. **Code Quality**: SonarQube analysis + dependency check
6. **Package Backend**: Create JAR file
7. **Build Frontend**: Build Angular application
8. **Build Docker Images**: Create container images
9. **Integration Tests**: Run API tests
10. **Deploy Staging**: Deploy to staging environment
11. **Deploy Production**: Deploy to production (manual approval)

## Next Steps

1. Set up Jenkins server
2. Install required plugins
3. Configure global tools
4. Set up credentials
5. Create pipeline job
6. Test with a sample commit
7. Configure notifications
8. Set up monitoring

For questions or issues, contact the DevOps team.
