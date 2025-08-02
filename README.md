# User Management API

A Spring Boot REST API with JWT authentication, role-based security, and **comprehensive monitoring stack** including Grafana, AppDynamics, and Dynatrace integration.

## 🚀 Quick Start Options

### Option 1: Quick Test (5 minutes, no Docker needed)
```bash
# Test API with H2 database - handles everything automatically
quick-test.bat
### Option 2: Full Monitoring Stack (15 minutes, requires Docker)
```bash
# Complete monitoring with Grafana, Prometheus, PostgreSQL
test-monitoring-stack.bat
```

## 🌟 What's Included

### 🗄️ Production Database
- **PostgreSQL 15** with optimized configuration
- **Connection pooling** with HikariCP
- **Database migrations** with Flyway
- **Backup/restore** automation

### 📊 Comprehensive Monitoring Stack
- **Grafana dashboards** for real-time visualization
- **Prometheus metrics** collection and alerting
- **SonarQube** for code quality analysis
- **AppDynamics & Dynatrace** integration ready
- **pgAdmin** for database management

### 🧪 Automated Testing
- **Load testing scripts** with realistic scenarios
- **Performance monitoring** during tests
- **Health check automation**
- **Cross-platform support** (Windows/Linux/Mac)

## 📊 Monitoring Dashboards

| Service | URL | Credentials | Purpose |
|---------|-----|-------------|---------|
| **🌐 Your API** | http://localhost:8080 | N/A | API endpoints & health |
| **📊 Grafana** | http://localhost:3000 | admin/admin | Visual dashboards |
| **📈 Prometheus** | http://localhost:9090 | N/A | Raw metrics & queries |
| **🔍 SonarQube** | http://localhost:9000 | admin/admin | Code quality analysis |
| **🗄️ pgAdmin** | http://localhost:8082 | admin@example.com/admin123 | Database management |
| **⚡ cAdvisor** | http://localhost:8081 | N/A | Container monitoring |

## 🧪 Load Testing Examples

```bash
# Light testing (development)
test-scripts\load-test.bat 5 60 1

# Realistic load (staging)  
test-scripts\load-test.bat 20 300 0.5

# Heavy load (production simulation)
test-scripts\load-test.bat 50 600 0.1

# Stress testing (find system limits)
test-scripts\load-test.bat 100 900 0.05
```

**Parameters**: `[concurrent_users] [duration_seconds] [delay_between_requests]`

## 📈 Key Metrics Monitored

### 🎯 Performance Metrics
- ⏱️ **Response Time**: 50th, 90th, 95th, 99th percentiles
- 🚀 **Throughput**: Requests per second
- ❌ **Error Rate**: HTTP 4xx/5xx responses
- 💾 **Memory Usage**: JVM heap and non-heap
- 🔗 **Database Connections**: Active/idle pool usage
- 🖥️ **CPU Utilization**: System resource usage

### 🏆 Success Criteria
- ✅ **Excellent**: Response time < 500ms (95th percentile), Error rate < 1%
- ⚠️ **Good**: Response time < 1s, Error rate < 2%
- ❌ **Needs Attention**: Response time > 1s, Error rate > 5%

## 🔧 Quick Setup Commands

### Start Full Monitoring Stack
```bash
# Start everything (PostgreSQL + monitoring)
docker-compose -f docker-compose.monitoring.yml up -d

# Check services status
docker-compose -f docker-compose.monitoring.yml ps

# View logs
docker-compose -f docker-compose.monitoring.yml logs -f
```

### Test API Immediately
```bash
# Health check
curl http://localhost:8080/actuator/health

# Metrics endpoint
curl http://localhost:8080/actuator/metrics

# Prometheus metrics
curl http://localhost:8080/actuator/prometheus
```

## 🛡️ Security Features

### 🔐 Authentication & Authorization
- **JWT-based authentication** with configurable expiration
- **Role-based access control** (USER, ADMIN, MODERATOR)
- **BCrypt password encryption** for secure storage
- **Spring Security** integration with method-level security
- **CORS support** for cross-origin requests

### 🔑 Security Best Practices
- **Token-based stateless authentication**
- **Password validation** with strength requirements
- **Secure headers** and CSRF protection
- **Input validation** on all API endpoints
- **Audit logging** for security events

## 🗄️ Database Configuration

### 📊 Production Database (PostgreSQL)
```yaml
Default Configuration:
- Database: userdb
- Host: localhost:5432  
- User: dbuser
- Connection Pool: HikariCP (optimized)
- Migrations: Flyway for version control
- Backup: Automated scripts included
```

### 🧪 Development Database (H2)
```yaml
Testing Configuration:
- In-memory database for quick testing
- H2 Console: http://localhost:8080/h2-console
- No setup required - works out of box
- Perfect for development and CI/CD
```

### ⚡ Quick Database Setup
```bash
# Windows - Full setup with monitoring
setup-postgres-monitoring.bat

# Linux/Mac - Full setup with monitoring  
./setup-postgres-monitoring.sh

# Test connection
test-postgres-connection.bat
```

## 🚀 Running the Application

### 🔥 Quick Start Methods

#### Method 1: Maven (Simplest)
```bash
# Start with PostgreSQL (recommended)
mvn spring-boot:run

# Start with H2 for testing
mvn spring-boot:run -Dspring.profiles.active=h2
```

#### Method 2: Docker Compose (Full Stack)
```bash
# Windows - Start everything (API + Database + Monitoring)
start-monitoring-postgres.bat

# Linux/Mac - Start everything
./start-monitoring-postgres.sh

# Or manually:
docker-compose -f docker-compose.monitoring.yml up -d
```

#### Method 3: IDE Run
- Import project in IDE (IntelliJ/Eclipse)
- Run `UserManagementApiApplication.java`
- Application starts on `http://localhost:8080`

### 🎯 Verify Application
```bash
# Health check
curl http://localhost:8080/actuator/health

# API test
curl http://localhost:8080/api/users/all

# Expected response: 200 OK
```

## 🧪 Testing & Load Testing

### 🏃‍♂️ Quick Testing
```bash
# Windows - Quick H2 test (no setup required)
quick-test.bat

# Full monitoring stack test
test-monitoring-stack.bat

# Load testing with realistic scenarios
load-test.bat --users 100 --duration 60s
```

### 📊 Load Testing Examples
```bash
# Light load test
load-test.bat --users 10 --duration 30s

# Medium load test  
load-test.bat --users 50 --duration 120s

# Heavy load test
load-test.bat --users 200 --duration 300s

# Stress test
load-test.bat --users 500 --duration 60s --ramp-up 30s
```

### 🎯 Professional JMeter Load Testing
```bash
# Windows - Professional load testing with detailed reporting
run-jmeter-test.bat 50 60 300

# Linux/Mac - Professional load testing
./run-jmeter-test.sh 50 60 300

# Parameters: [users] [ramp_time_seconds] [duration_seconds]
# Generates: HTML reports, performance charts, detailed metrics
```

### 🔍 SonarQube Code Quality Analysis
```bash
# Windows - Comprehensive code quality analysis
run-sonarqube-analysis.bat

# Linux/Mac - Comprehensive code quality analysis
./run-sonarqube-analysis.sh

# Analyzes: bugs, vulnerabilities, code smells, coverage, duplications
# Quality Gates: A/B ratings for production readiness
```

### 🔍 Monitoring During Tests
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **SonarQube**: http://localhost:9000 (admin/admin)
- **Application Metrics**: http://localhost:8080/actuator/metrics

## 📋 Example Usage

### 🔑 Authentication Flow
```bash
# 1. Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com", 
    "password": "SecurePassword123!",
    "firstName": "Test",
    "lastName": "User"
  }'

# 2. Login to get JWT token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "SecurePassword123!"
  }'

# Response includes JWT token:
# {"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."}
```

### 👤 User Management
```bash
# Get all users (with authentication)
curl -X GET http://localhost:8080/api/users/all \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Get user profile
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Update user profile
curl -X PUT http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Updated",
    "lastName": "Name",
    "email": "updated@example.com"
  }'
```

### 📊 Monitoring & Health
```bash
# Application health
curl http://localhost:8080/actuator/health

# Detailed metrics
curl http://localhost:8080/actuator/metrics

# Database health
curl http://localhost:8080/actuator/health/db

# Prometheus metrics for monitoring
curl http://localhost:8080/actuator/prometheus
```

## 🔗 API Endpoints

### 🔐 Authentication Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | User login | No |
| POST | `/api/auth/refresh` | Refresh JWT token | Yes |
| POST | `/api/auth/logout` | User logout | Yes |

### 👥 User Management Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/all` | Get all users | Yes (ADMIN) |
| GET | `/api/users/profile` | Get current user profile | Yes |
| PUT | `/api/users/profile` | Update user profile | Yes |
| DELETE | `/api/users/{id}` | Delete user | Yes (ADMIN) |
| GET | `/api/users/{username}` | Get user by username | Yes |

### 📈 Monitoring & Health Endpoints  
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/actuator/health` | Application health | No |
| GET | `/actuator/metrics` | Application metrics | No |
| GET | `/actuator/prometheus` | Prometheus metrics | No |
| GET | `/actuator/info` | Application info | No |

## 📊 Monitoring Stack

### 🎛️ Quick Access Dashboard
| Service | URL | Credentials | Purpose |
|---------|-----|-------------|---------|
| **🚀 Application** | http://localhost:8080 | None | Main API Server |
| **🐘 PostgreSQL** | localhost:5432 | dbuser / SecureP@ssw0rd123 | Production Database |
| **🗃️ pgAdmin** | http://localhost:8082 | admin@example.com / admin123 | Database Management |
| **📈 Grafana** | http://localhost:3000 | admin / admin | Metrics Visualization |
| **🔍 Prometheus** | http://localhost:9090 | None | Metrics Collection |
| **🔍 SonarQube** | http://localhost:9000 | admin / admin | Code Quality & Security Analysis |

### 🏃‍♂️ Load Testing Commands
```bash
# Windows
# Light load (10 users, 30 seconds)
load-test.bat --users 10 --duration 30s

# Production simulation (100 users, 2 minutes) 
load-test.bat --users 100 --duration 120s

# Professional JMeter testing (50 users, 60s ramp, 5 min duration)
run-jmeter-test.bat 50 60 300

# Code quality analysis
run-sonarqube-analysis.bat

# Linux/Mac
./load-test.sh --users 100 --duration 60s
./run-jmeter-test.sh 50 60 300
./run-sonarqube-analysis.sh
```

### 📊 Key Metrics to Monitor
- **Response Time**: < 200ms for 95% of requests
- **Error Rate**: < 1% under normal load
- **CPU Usage**: < 80% under normal load  
- **Memory Usage**: < 1GB heap usage
- **Database Connections**: Monitor pool utilization
- **Active Users**: Track concurrent user sessions
- **Code Quality**: SonarQube A/B ratings (bugs, vulnerabilities, code smells)
- **Test Coverage**: > 80% unit test coverage
- **Technical Debt**: < 10 code smells, 0 critical issues

### 🎯 Performance Targets
```yaml
Response Time Targets:
  Authentication: < 100ms
  User Queries: < 150ms  
  User Updates: < 200ms
  Health Checks: < 50ms

Load Capacity:
  Concurrent Users: 500+
  Requests/Second: 1000+
  Daily Active Users: 10,000+

Quality Targets:
  SonarQube Rating: A or B
  Code Coverage: > 80%
  Bugs: 0 critical/major
  Security Vulnerabilities: 0
```

### 📈 Monitoring Integration
- **Grafana Dashboards**: Pre-configured for API metrics
- **Prometheus Alerts**: Automatic alerting for issues
- **SonarQube Analysis**: Code quality and security scanning with quality gates
- **JMeter Load Testing**: Professional load testing with HTML reports
- **AppDynamics Ready**: APM integration available
- **Dynatrace Compatible**: Full-stack monitoring ready
- **ELK Stack Ready**: Logging integration prepared
- **Quality Gates**: Automated code quality checks and thresholds

### 🧪 Testing Tools Integration
- **Unit Testing**: JUnit 5 with 80%+ coverage requirement
- **Integration Testing**: Spring Boot Test with TestContainers
- **Load Testing**: JMeter professional test plans with realistic scenarios
- **Code Quality**: SonarQube with comprehensive quality gates
- **Security Testing**: OWASP dependency check and vulnerability scanning
- **Performance Monitoring**: Real-time metrics with Grafana/Prometheus

For complete monitoring setup and testing guide, see: `MONITORING-TESTING-GUIDE.md`

### Database Management
```bash
# Backup database
backup-postgres.bat  # Windows
./backup-postgres.sh # Linux/Mac

# Restore database  
restore-postgres.bat backup_file.zip     # Windows
./restore-postgres.sh backup_file.sql.gz # Linux/Mac

# Test connection
test-postgres.bat    # Windows
```

## Usage Examples

### Register a new user
```bash
curl -X POST http://localhost:8080/api/v1/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "username": "johndoe", 
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "password123"
  }'
```

### Access protected endpoint
```bash
curl -X GET http://localhost:8080/api/v1/users/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Check application health
```bash
curl http://localhost:8080/actuator/health
```

## 🏗️ Project Structure

```
user-management-api/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/kumar/wipro/api/
│   │   │   ├── 📁 config/          # Security, data & monitoring config
│   │   │   ├── 📁 controller/      # REST controllers  
│   │   │   ├── 📁 dto/            # Data transfer objects
│   │   │   ├── 📁 model/          # JPA entities
│   │   │   ├── 📁 repository/     # Data repositories
│   │   │   ├── 📁 security/       # Security components
│   │   │   └── 📁 service/        # Business logic services
│   │   └── 📁 resources/
│   │       ├── 📄 application.properties
│   │       ├── 📄 application-h2.properties  
│   │       └── 📁 db/migration/   # Flyway database migrations
│   └── 📁 test/                   # Unit and integration tests
├── 📁 database/                   # PostgreSQL setup scripts  
├── 📁 monitoring/                 # Grafana dashboards & configs
├── 📁 test-scripts/              # Load testing & automation
├── 📄 docker-compose.monitoring.yml  # Full monitoring stack
├── 📄 MONITORING-TESTING-GUIDE.md    # Complete testing guide
└── 📄 README.md                  # This comprehensive guide
```

## 🛠️ Technologies Used

### 🚀 Backend Stack
- **Spring Boot 3.2.0** - Modern Java framework
- **Spring Security** - Authentication & authorization  
- **Spring Data JPA** - Database abstraction
- **JWT** - Stateless authentication tokens
- **BCrypt** - Secure password hashing

### 🗄️ Database & Persistence  
- **PostgreSQL 15** - Production database
- **H2 Database** - Testing and development
- **Flyway** - Database version control
- **HikariCP** - High-performance connection pooling

### 📊 Monitoring & Observability
- **Grafana** - Metrics visualization and dashboards
- **Prometheus** - Metrics collection and alerting
- **SonarQube** - Code quality and security analysis
- **Spring Boot Actuator** - Application monitoring
- **pgAdmin** - PostgreSQL database management

### 🔧 Development & DevOps
- **Maven** - Build automation and dependency management
- **Docker & Docker Compose** - Containerization
- **Jenkins** - CI/CD pipelines  
- **Git** - Version control
- **Java 17** - LTS Java version

## 📚 Installation & Setup

### ⚡ Prerequisites
- **Java 17+** (OpenJDK or Oracle JDK)
- **Maven 3.6+** for build automation
- **Docker & Docker Compose** for containerization
- **Git** for version control
- **4GB+ RAM** for full monitoring stack

### 🚀 Quick Install (Windows)
```powershell
# Run as Administrator
.\install-prerequisites.ps1
```

### 🐧 Quick Install (Linux/Mac)
```bash
# Ubuntu/Debian
sudo apt update && sudo apt install openjdk-17-jdk maven docker.io docker-compose git

# macOS (with Homebrew)
brew install openjdk@17 maven docker docker-compose git
```

### 🎯 Quick Start
```bash
# 1. Clone repository
git clone https://github.com/mitkumar1/user-management-api.git
cd user-management-api

# 2. Start with full monitoring stack
start-monitoring-postgres.bat  # Windows
./start-monitoring-postgres.sh # Linux/Mac

# 3. Verify application
curl http://localhost:8080/actuator/health

# 4. Access monitoring
# Grafana: http://localhost:3000 (admin/admin)
# Application: http://localhost:8080
```

## 📖 Documentation

### � Quick Reference Guides
- 📖 **[INSTALLATION-GUIDE.md](INSTALLATION-GUIDE.md)** - Complete setup instructions
- 🐘 **[POSTGRESQL-SETUP.md](POSTGRESQL-SETUP.md)** - Database configuration  
- 📊 **[MONITORING-TESTING-GUIDE.md](MONITORING-TESTING-GUIDE.md)** - Complete monitoring setup
- 🔧 **[JENKINS-MULTIBRANCH-SETUP.md](JENKINS-MULTIBRANCH-SETUP.md)** - CI/CD pipeline

### 🎯 Testing Scripts  
- 🏃‍♂️ **`quick-test.bat`** - Fast H2 testing (no setup required)
- 📊 **`load-test.bat`** - Comprehensive load testing
- 🔍 **`test-monitoring-stack.bat`** - Full stack verification
- 🗃️ **`test-postgres-connection.bat`** - Database connectivity test
- 🎯 **`run-jmeter-test.bat`** - Professional JMeter load testing
- 🔍 **`run-sonarqube-analysis.bat`** - Code quality and security analysis
- 📈 **`jmeter-load-test.jmx`** - JMeter test plan configuration

## 🔄 Development Workflow

### 🌟 Feature Development
```bash
# 1. Create feature branch
git checkout -b feature/your-awesome-feature

# 2. Make changes and test locally  
mvn test
quick-test.bat  # Quick verification

# 3. Run quality checks
mvn sonar:sonar

# 4. Load test your changes
load-test.bat --users 50 --duration 60s

# 5. Run JMeter professional testing
run-jmeter-test.bat 30 30 180

# 6. Commit and push
git add . && git commit -m "Add awesome feature"
git push origin feature/your-awesome-feature
```

### 📋 Quality Gates
- ✅ **Unit Tests**: 80%+ code coverage
- ✅ **Integration Tests**: All endpoints tested
- ✅ **SonarQube**: A/B quality rating, 0 critical issues
- ✅ **Load Testing**: Handles 500+ concurrent users (JMeter validated)
- ✅ **Security**: No vulnerabilities detected (SonarQube + OWASP)
- ✅ **Performance**: < 200ms response time (95th percentile)
- ✅ **Code Review**: 1 approval required

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

### 🎯 How to Contribute
1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Make** your changes with tests
4. **Run** quality checks (`mvn test sonar:sonar`)
5. **Load test** your changes (`load-test.bat`)
6. **Create** a Pull Request

### 📏 Code Standards
- **Java 17** features and best practices
- **Spring Boot** conventions and patterns
- **RESTful** API design principles
- **Security** best practices (JWT, encryption)
- **Testing** - comprehensive unit and integration tests
- **Documentation** - clear comments and README updates

## 🆘 Support & Help

### 🔍 Getting Help
- 📧 **Issues**: [GitHub Issues](https://github.com/mitkumar1/user-management-api/issues)
- 📖 **Documentation**: See `docs/` folder and guides above
- 🤝 **Contributing**: See `CONTRIBUTING.md`
- 💬 **Discussions**: GitHub Discussions for questions

### 🐛 Common Issues & Solutions
```bash
# Issue: Port 8080 already in use
# Solution: Stop existing application or change port
mvn spring-boot:run -Dserver.port=8081

# Issue: Database connection fails
# Solution: Verify PostgreSQL is running
test-postgres-connection.bat

# Issue: Monitoring stack fails to start
# Solution: Check Docker resources and ports
docker-compose -f docker-compose.monitoring.yml logs
```

---

**🚀 Happy coding! Build amazing applications with confidence!**
