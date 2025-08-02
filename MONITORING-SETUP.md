# Monitoring and Code Quality Setup

## Overview
This guide sets up comprehensive monitoring and code quality analysis for the User Management API using:
- **Grafana**: Dashboards and visualization
- **AppDynamics**: Application Performance Monitoring (APM)
- **Dynatrace**: Full-stack monitoring and AI-powered insights
- **SonarQube**: Code quality and security analysis

## 1. SonarQube Setup

### Local SonarQube with Docker

```bash
# Pull and run SonarQube
docker run -d --name sonarqube \
  -p 9000:9000 \
  -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
  sonarqube:latest

# Access SonarQube at http://localhost:9000
# Default credentials: admin/admin
```

### Maven Integration

Add SonarQube plugin to `pom.xml`:

```xml
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.10.0.2594</version>
</plugin>
```

### SonarQube Analysis Commands

```bash
# Run SonarQube analysis
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=user-management-api \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token

# With coverage
mvn clean verify sonar:sonar \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

## 2. Grafana Setup

### Docker Compose for Grafana + Prometheus

```yaml
# docker-compose.monitoring.yml
version: '3.8'
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
      - '--web.console.libraries=/etc/prometheus/console_libraries'
      - '--web.console.templates=/etc/prometheus/consoles'

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana-storage:/var/lib/grafana
      - ./monitoring/grafana/provisioning:/etc/grafana/provisioning

volumes:
  grafana-storage:
```

### Spring Boot Metrics Configuration

Add to `application.properties`:

```properties
# Actuator endpoints
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.metrics.export.prometheus.enabled=true
management.endpoint.prometheus.enabled=true
management.endpoint.metrics.enabled=true

# Custom metrics
management.metrics.tags.application=user-management-api
management.metrics.tags.environment=dev
```

## 3. AppDynamics Setup

### AppDynamics Agent Configuration

Add AppDynamics dependency to `pom.xml`:

```xml
<dependency>
    <groupId>com.appdynamics</groupId>
    <artifactId>appdynamics-agent</artifactId>
    <version>23.1.0</version>
</dependency>
```

### JVM Arguments for AppDynamics

```bash
# Add to your application startup
-javaagent:/path/to/appd/javaagent.jar
-Dappdynamics.agent.applicationName=UserManagementAPI
-Dappdynamics.agent.tierName=Backend
-Dappdynamics.agent.nodeName=Node1
-Dappdynamics.controller.hostName=your-controller.appdynamics.com
-Dappdynamics.controller.port=443
-Dappdynamics.agent.accountName=your-account
-Dappdynamics.agent.accountAccessKey=your-access-key
```

### AppDynamics Configuration File

Create `appd-agent.properties`:

```properties
# AppDynamics Agent Configuration
agent.applicationName=UserManagementAPI
agent.tierName=Backend
agent.nodeName=${HOSTNAME}
controller.hostName=your-controller.appdynamics.com
controller.port=443
controller.ssl.enabled=true
agent.accountName=your-account
agent.accountAccessKey=your-access-key

# Business Transaction Detection
agent.named.transaction.detection.enabled=true
agent.exit.call.detection.enabled=true
```

## 4. Dynatrace Setup

### Dynatrace OneAgent

```bash
# Download and install OneAgent (Linux)
wget -O Dynatrace-OneAgent.sh "https://[your-environment].live.dynatrace.com/api/v1/deployment/installer/agent/unix/default/latest?arch=x86&flavor=default" --header="Authorization: Api-Token [your-token]"
sudo /bin/sh Dynatrace-OneAgent.sh --set-app-log-content-access=true
```

### Dynatrace Spring Boot Integration

Add to `application.properties`:

```properties
# Dynatrace configuration
dynatrace.oneagent.enabled=true
dynatrace.metadata.service.name=user-management-api
dynatrace.metadata.service.version=1.0.0
dynatrace.metadata.environment=development
```

### Dynatrace Custom Metrics

```java
@Component
public class DynatraceMetrics {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        Counter.builder("user.registration.count")
            .tag("status", "success")
            .register(meterRegistry)
            .increment();
    }
    
    @EventListener
    public void handleUserLogin(UserLoginEvent event) {
        Timer.Sample sample = Timer.start(meterRegistry);
        sample.stop(Timer.builder("user.login.duration")
            .tag("method", event.getMethod())
            .register(meterRegistry));
    }
}
```

## 5. Docker Compose Integration

### Complete Monitoring Stack

```yaml
# docker-compose.full-monitoring.yml
version: '3.8'
services:
  # Your application
  user-management-api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=monitoring
    depends_on:
      - postgres
      - prometheus
    volumes:
      - ./monitoring/appd:/opt/appd

  # Database
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"

  # Monitoring Stack
  prometheus:
    image: prom/prometheus:latest
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml

  grafana:
    image: grafana/grafana:latest
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - ./monitoring/grafana:/etc/grafana/provisioning

  # SonarQube
  sonarqube:
    image: sonarqube:latest
    ports:
      - "9000:9000"
    environment:
      - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_logs:/opt/sonarqube/logs
      - sonarqube_extensions:/opt/sonarqube/extensions

volumes:
  sonarqube_data:
  sonarqube_logs:
  sonarqube_extensions:
```

## 6. Jenkins Integration

### Update Jenkinsfile for Code Quality

```groovy
pipeline {
    agent any
    
    tools {
        maven 'Maven3'
        jdk 'JDK11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                sh 'mvn clean compile test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn clean verify sonar:sonar'
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    def image = docker.build("user-management-api:${env.BUILD_NUMBER}")
                    image.push()
                    image.push("latest")
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            emailext (
                subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Build succeeded with all quality gates passed.",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
        failure {
            emailext (
                subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: "Build failed. Check SonarQube for quality issues.",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}
```

## 7. Monitoring Configuration Files

### Prometheus Configuration

Create `monitoring/prometheus.yml`:

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'user-management-api'
    static_configs:
      - targets: ['localhost:8080']
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s

  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']
```

### Grafana Dashboard

Create `monitoring/grafana/dashboards/app-dashboard.json`:

```json
{
  "dashboard": {
    "title": "User Management API",
    "panels": [
      {
        "title": "HTTP Requests",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_server_requests_seconds_count[5m])",
            "legendFormat": "{{method}} {{uri}}"
          }
        ]
      },
      {
        "title": "Response Times",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_server_requests_seconds_sum[5m]) / rate(http_server_requests_seconds_count[5m])",
            "legendFormat": "Average Response Time"
          }
        ]
      }
    ]
  }
}
```

## 8. Quick Start Commands

```bash
# 1. Start monitoring stack
docker-compose -f docker-compose.full-monitoring.yml up -d

# 2. Run SonarQube analysis
mvn clean verify sonar:sonar

# 3. Access dashboards
# Grafana: http://localhost:3000 (admin/admin)
# SonarQube: http://localhost:9000 (admin/admin)
# Prometheus: http://localhost:9090

# 4. Run application with monitoring
mvn spring-boot:run -Dspring.profiles.active=monitoring
```

## 9. Key Metrics to Monitor

### Application Metrics
- HTTP request rates and response times
- Database connection pool usage
- JVM memory and GC metrics
- Custom business metrics

### Infrastructure Metrics
- CPU and memory utilization
- Network I/O
- Disk usage
- Container metrics

### Code Quality Metrics
- Code coverage
- Technical debt
- Security vulnerabilities
- Code smells and bugs

## 10. Alerting Rules

### Grafana Alerts
- High response times (> 500ms)
- Error rate > 5%
- Memory usage > 80%
- Database connection failures

### SonarQube Quality Gates
- Code coverage < 80%
- Duplicated lines > 3%
- Critical issues > 0
- Security hotspots > 0
