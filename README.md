# User Management API

A Spring Boot REST API with JWT authentication and role-based security.

## Features

- User registration and authentication
- JWT token-based security
- Role-based access control (USER, ADMIN)
- Password encryption using BCrypt
- H2 in-memory database for development
- MySQL support for production
- RESTful API endpoints

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

## API Endpoints

### Authentication
- `POST /api/v1/auth/signup` - Register a new user
- `POST /api/v1/auth/signin` - User login

### Users
- `GET /api/v1/users/me` - Get current user profile (requires authentication)
- `GET /api/v1/users/{username}` - Get user by username

## Database Configuration

### Development (H2)
The application uses H2 in-memory database by default for development:
- Database URL: `jdbc:h2:mem:testdb`
- H2 Console: `http://localhost:8080/api/v1/h2-console`

### Production (MySQL)
Uncomment MySQL configuration in `application.properties` for production:
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

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/kumar/wipro/api/
│   │       ├── config/          # Security and data configuration
│   │       ├── controller/      # REST controllers
│   │       ├── dto/            # Data transfer objects
│   │       ├── model/          # JPA entities
│   │       ├── repository/     # Data repositories
│   │       ├── security/       # Security components
│   │       └── service/        # Business logic services
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/kumar/wipro/api/
```

## Technologies Used

- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- H2 Database (development)
- MySQL (production)
- Maven
- Java 17
