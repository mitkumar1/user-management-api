# User Management System - Full Stack Application

## Overview

This is a complete full-stack user management system built with:

- **Backend**: Spring Boot 3.2.0 with JWT Security
- **Frontend**: Angular 16 with Material Design
- **Database**: H2 (in-memory) / Can be configured for PostgreSQL/MySQL
- **Security**: JWT Token-based Authentication

## Architecture

```
┌─────────────────┐    HTTP/REST    ┌─────────────────┐
│                 │ ──────────────> │                 │
│  Angular 16     │                 │  Spring Boot    │
│  Frontend       │ <────────────── │  Backend        │
│  (Port 4200)    │    JSON/JWT     │  (Port 8080)    │
└─────────────────┘                 └─────────────────┘
                                           │
                                           │ JPA/Hibernate
                                           ▼
                                    ┌─────────────────┐
                                    │                 │
                                    │  H2 Database    │
                                    │  (In-Memory)    │
                                    └─────────────────┘
```

## Features

### Backend Features
- ✅ JWT Authentication & Authorization
- ✅ Role-based Access Control (USER/ADMIN)
- ✅ RESTful API endpoints
- ✅ Password encryption (BCrypt)
- ✅ H2 Database with JPA/Hibernate
- ✅ Spring Security configuration
- ✅ CORS configuration for frontend
- ✅ Input validation
- ✅ Exception handling
- ✅ Comprehensive test suite

### Frontend Features
- ✅ Angular 16 with TypeScript
- ✅ Material Design UI components
- ✅ Responsive design (mobile-friendly)
- ✅ JWT token management
- ✅ Route guards for authentication
- ✅ HTTP interceptors
- ✅ Form validation
- ✅ Error handling with user feedback
- ✅ Role-based UI features

## Quick Start

### Prerequisites
1. **Java 21** - [Download here](https://adoptium.net/)
2. **Maven 3.6+** - [Download here](https://maven.apache.org/download.cgi)
3. **Node.js 16+** - [Download here](https://nodejs.org/)
4. **Angular CLI 16** - Install with `npm install -g @angular/cli@16`

### Backend Setup

1. **Clone and navigate to the project:**
   ```bash
   git clone <repository-url>
   cd user-management-api
   ```

2. **Run the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   mvn clean package
   java -jar target/user-management-api-1.0.0-SNAPSHOT.jar
   ```

3. **Verify backend is running:**
   - Application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (empty)

### Frontend Setup

1. **Install dependencies and run:**
   ```bash
   # Windows
   setup-frontend.bat
   
   # Unix/Linux/Mac
   chmod +x setup-frontend.sh
   ./setup-frontend.sh
   ```

2. **Or manually:**
   ```bash
   cd frontend/user-management-ui
   npm install
   npm start
   ```

3. **Access the application:**
   - Frontend: http://localhost:4200

## API Endpoints

### Authentication Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |

### User Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/profile` | Get current user profile | Yes |
| GET | `/api/users/admin` | Admin only endpoint | Yes (Admin) |

## Frontend Routes

| Route | Component | Description | Auth Required |
|-------|-----------|-------------|---------------|
| `/` | Dashboard | Redirects to dashboard | Yes |
| `/login` | Login | User login form | No |
| `/register` | Register | User registration form | No |
| `/dashboard` | Dashboard | Main dashboard | Yes |
| `/profile` | Profile | User profile page | Yes |

## Usage Examples

### 1. Register a New User

**Frontend:**
- Navigate to http://localhost:4200/register
- Fill in the registration form
- Submit to create account

**API Call:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "name": "Test User"
  }'
```

### 2. Login

**Frontend:**
- Navigate to http://localhost:4200/login
- Enter credentials and login

**API Call:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer"
}
```

### 3. Access Protected Profile

**Frontend:**
- After login, click on profile in the user menu

**API Call:**
```bash
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer <your-jwt-token>"
```

## Development

### Backend Development
```bash
# Run with hot reload
mvn spring-boot:run

# Run tests
mvn test

# Build for production
mvn clean package
```

### Frontend Development
```bash
cd frontend/user-management-ui

# Development server with hot reload
ng serve

# Run tests
ng test

# Build for production
ng build --configuration production
```

## Configuration

### Backend Configuration (`application.properties`)
```properties
# Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true

# JWT
app.jwtSecret=mySecretKey
app.jwtExpirationInMs=86400000

# Server
server.port=8080
```

### Frontend Configuration (`proxy.conf.json`)
```json
{
  "/api/*": {
    "target": "http://localhost:8080",
    "secure": true,
    "changeOrigin": true,
    "logLevel": "debug"
  }
}
```

## Security

### Authentication Flow
1. User submits credentials to `/api/auth/login`
2. Backend validates credentials and returns JWT token
3. Frontend stores token in localStorage
4. Token is automatically attached to subsequent API requests
5. Backend validates token on each protected endpoint

### Authorization Levels
- **Public**: Registration, Login
- **User**: Profile access, Dashboard
- **Admin**: All user features + admin panel

## Testing

### Backend Tests
```bash
mvn test
```

Test Coverage:
- Controller tests
- Service tests
- Security configuration tests
- Integration tests

### Frontend Tests
```bash
cd frontend/user-management-ui
ng test
```

## Deployment

### Backend Deployment
1. **Build JAR:**
   ```bash
   mvn clean package
   ```

2. **Run with production profile:**
   ```bash
   java -jar target/user-management-api-1.0.0-SNAPSHOT.jar
   ```

### Frontend Deployment
1. **Build for production:**
   ```bash
   ng build --configuration production
   ```

2. **Serve static files:**
   Deploy `dist/user-management-ui` to any web server (Nginx, Apache, etc.)

### Docker Deployment
```bash
# Build Docker image
docker build -t user-management-api .

# Run container
docker run -p 8080:8080 user-management-api
```

## Troubleshooting

### Common Issues

1. **CORS Errors:**
   - Ensure CORS is configured for `http://localhost:4200`
   - Check browser console for specific CORS errors

2. **JWT Token Issues:**
   - Check token expiration
   - Verify token format in localStorage
   - Check Authorization header format

3. **Database Connection:**
   - H2 console: http://localhost:8080/h2-console
   - Check application.properties for database settings

4. **Port Conflicts:**
   - Backend: Change `server.port` in application.properties
   - Frontend: Use `ng serve --port 4201`

### Debug Tips

1. **Backend Debugging:**
   - Enable debug logging: `logging.level.com.kumar.wipro.api=DEBUG`
   - Check console output for errors
   - Use H2 console to inspect database

2. **Frontend Debugging:**
   - Open browser developer tools
   - Check Network tab for API calls
   - Check Console for JavaScript errors
   - Use Angular DevTools extension

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For questions or issues:
1. Check this documentation
2. Review the troubleshooting section
3. Check the application logs
4. Create an issue in the repository
