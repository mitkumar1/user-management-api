# User Management API

A Spring Boot REST API with JWT authentication, role-based security, and comprehensive monitoring.

## Features

- User registration and authentication
- JWT token-based security
- Role-based access control (USER, ADMIN, MODERATOR)
- Password encryption using BCrypt
- **PostgreSQL production database** with connection pooling
- **Database migrations** with Flyway
- **Comprehensive monitoring** with Grafana, Prometheus, SonarQube
- **Application Performance Monitoring** with AppDynamics & Dynatrace
- RESTful API endpoints with validation
- Audit logging and performance tracking

## Security Features Enabled

1. **Spring Security Configuration**
   - JWT-based authentication
   - Role-based authorization
   - CORS support
   - CSRF protection disabled for stateless API

2. **Password Security**
   - BCrypt password encoding
   - Strong password validation

3. **JWT Security**
   - Token-based authentication
   - Configurable token expiration
   - Secure token validation

## Database Configuration

### Production (PostgreSQL) - Default
The application now uses PostgreSQL as the default database:
- **Database**: `userdb`
- **Host**: `localhost:5432`
- **User**: `dbuser`
- **Connection Pool**: HikariCP with optimized settings
- **Migrations**: Flyway for version control

### Quick Setup
```bash
# Windows
start-monitoring-postgres.bat

# Linux/Mac  
./start-monitoring-postgres.sh
```

### Development/Testing (H2)
H2 in-memory database available for testing profile:
- Database URL: `jdbc:h2:mem:testdb`
- H2 Console: `http://localhost:8080/h2-console`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. The API will be available at: `http://localhost:8080/api/v1`

## Example Usage

## API Endpoints

### Authentication
- `POST /api/v1/auth/signup` - Register a new user
- `POST /api/v1/auth/signin` - User login

### Users  
- `GET /api/v1/users/me` - Get current user profile (requires authentication)
- `GET /api/v1/users/{username}` - Get user by username

### Monitoring & Health
- `GET /actuator/health` - Application health status
- `GET /actuator/metrics` - Application metrics
- `GET /actuator/prometheus` - Prometheus metrics endpoint

## Monitoring Stack

### Quick Access URLs
- **Application**: http://localhost:8080
- **PostgreSQL**: localhost:5432 (dbuser/SecureP@ssw0rd123)
- **pgAdmin**: http://localhost:8082 (admin@example.com/admin123)
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **SonarQube**: http://localhost:9000 (admin/admin)

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

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/kumar/wipro/api/
│   │       ├── config/          # Security, data & monitoring config
│   │       ├── controller/      # REST controllers
│   │       ├── dto/            # Data transfer objects
│   │       ├── model/          # JPA entities
│   │       ├── repository/     # Data repositories
│   │       ├── security/       # Security components
│   │       └── service/        # Business logic services
│   └── resources/
│       ├── db/migration/       # Flyway database migrations
│       ├── application.properties
│       └── application-production.properties
├── database/                   # PostgreSQL scripts
├── monitoring/                 # Monitoring configurations
└── test/
    └── java/
        └── com/kumar/wipro/api/
```

## Technologies Used

- **Backend**: Spring Boot 3.2.0, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 15, Flyway migrations, HikariCP
- **Monitoring**: Grafana, Prometheus, SonarQube, AppDynamics, Dynatrace
- **Security**: JWT (JSON Web Tokens), BCrypt password encryption
- **Build**: Maven, Java 17
- **DevOps**: Docker, Docker Compose, Jenkins
- **Code Quality**: SonarQube, JaCoCo code coverage

## Installation & Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- Git

### Quick Install (Windows)
```powershell
# Run as Administrator
.\install-prerequisites.ps1
```

### Manual Installation
See [INSTALLATION-GUIDE.md](INSTALLATION-GUIDE.md) for detailed instructions.

### Quick Start
```bash
# Clone repository
git clone https://github.com/mitkumar1/user-management-api.git
cd user-management-api

# Switch to feature branch
git checkout feature/profile-enhancements

# Start with PostgreSQL and monitoring
start-monitoring-postgres.bat  # Windows
./start-monitoring-postgres.sh # Linux/Mac

# Or build and run manually
mvn clean package
mvn spring-boot:run -Dspring.profiles.active=production
```

## Documentation

- 📖 [Installation Guide](INSTALLATION-GUIDE.md) - Prerequisites and setup
- 🐘 [PostgreSQL Setup](POSTGRESQL-SETUP.md) - Database configuration
- 📊 [Monitoring Setup](MONITORING-SETUP.md) - Grafana, SonarQube, etc.
- 🔧 [Jenkins Setup](JENKINS-MULTIBRANCH-SETUP.md) - CI/CD pipeline

## Development Workflow

1. **Create feature branch**: `git checkout -b feature/your-feature`
2. **Make changes**: Develop your feature
3. **Test locally**: `mvn test`
4. **Run quality checks**: `mvn sonar:sonar`
5. **Create PR**: Push branch and create Pull Request
6. **Jenkins build**: Automatic build and testing
7. **Code review**: 1 approval required
8. **Merge**: Merge to master after approval

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for your changes
5. Run quality checks
6. Create a Pull Request

## Support

- 📧 Issues: [GitHub Issues](https://github.com/mitkumar1/user-management-api/issues)
- 📖 Documentation: See docs folder
- 🤝 Contributing: See CONTRIBUTING.md

---

**Happy coding! 🚀**
