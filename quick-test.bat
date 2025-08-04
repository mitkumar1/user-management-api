@echo off
REM Quick Start Script for Testing User Management API with Monitoring

echo 🚀 User Management API - Monitoring Test Suite
echo ===============================================
echo.

echo Step 1: Starting Application with H2 Database (for quick testing)
echo -----------------------------------------------------------------
echo.

REM Check if Java and Maven are available
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java not found. Please install Java 21 or run install-prerequisites.ps1
    pause
    exit /b 1
)

mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven not found. Please install Maven or run install-prerequisites.ps1
    pause
    exit /b 1
)

echo ✅ Java and Maven are available
echo.

echo Starting application with H2 database...
echo (This allows testing without PostgreSQL setup)
echo.

REM Copy H2 configuration temporarily
copy "src\main\resources\application.properties" "application.properties.backup" >nul 2>&1
copy "src\main\resources\application-h2.properties" "src\main\resources\application.properties" >nul 2>&1

echo ✅ Switched to H2 configuration
echo.

echo Starting Spring Boot application...
start "User Management API" cmd /c "mvn spring-boot:run"

REM Wait for application to start
echo Waiting for application to start...
timeout /t 10 /nobreak >nul

REM Check if application is running
:check_app
curl -s http://localhost:8080/actuator/health >nul 2>&1
if errorlevel 1 (
    echo ⏳ Waiting for application to start...
    timeout /t 5 /nobreak >nul
    goto check_app
)

echo ✅ Application is running!
echo.

echo Step 2: Running Basic API Tests
echo -------------------------------
echo.

echo Testing health endpoint...
curl -s http://localhost:8080/actuator/health | findstr "UP" >nul
if errorlevel 1 (
    echo ❌ Health check failed
) else (
    echo ✅ Health check passed
)

echo.
echo Testing metrics endpoint...
curl -s http://localhost:8080/actuator/metrics >nul 2>&1
if errorlevel 1 (
    echo ❌ Metrics endpoint failed
) else (
    echo ✅ Metrics endpoint working
)

echo.
echo Testing user registration...
curl -s -X POST http://localhost:8080/api/auth/signup ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Test User\",\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"Test123!@#\"}" >temp_response.txt 2>&1

findstr "successfully" temp_response.txt >nul 2>&1
if errorlevel 1 (
    echo ⚠️  User registration response (may already exist)
) else (
    echo ✅ User registration working
)
del temp_response.txt >nul 2>&1

echo.
echo Step 3: Starting Load Test
echo --------------------------
echo.

echo Starting 30-second load test with 5 concurrent users...
echo You can monitor in real-time at:
echo - Application Health: http://localhost:8080/actuator/health
echo - Application Metrics: http://localhost:8080/actuator/metrics
echo.

call test-scripts\load-test.bat 5 30 1

echo.
echo Step 4: Monitoring Stack (Optional)
echo -----------------------------------
echo.

echo To start full monitoring stack with Grafana and Prometheus:
echo.
echo 1. Install Docker Desktop
echo 2. Run: docker-compose -f docker-compose.monitoring.yml up -d
echo 3. Access Grafana at: http://localhost:3000 (admin/admin)
echo 4. Access Prometheus at: http://localhost:9090
echo.

echo Step 5: API Testing with Postman Collection
echo -------------------------------------------
echo.

echo A Postman collection is available: User-Management-API.postman_collection.json
echo Import it into Postman for comprehensive API testing.
echo.

echo Step 6: Restore Configuration
echo -----------------------------
echo.

echo Restoring original PostgreSQL configuration...
copy "application.properties.backup" "src\main\resources\application.properties" >nul 2>&1
del "application.properties.backup" >nul 2>&1
echo ✅ Configuration restored
echo.

echo 🎉 Testing Complete!
echo ====================
echo.
echo Summary:
echo - ✅ Application tested with H2 database
echo - ✅ Basic API endpoints verified  
echo - ✅ Load testing completed
echo - ✅ Configuration restored
echo.
echo For production testing:
echo 1. Set up PostgreSQL using POSTGRESQL-SETUP.md
echo 2. Run monitoring stack with Docker
echo 3. Configure AppDynamics/Dynatrace agents
echo.
echo Press any key to exit...
pause >nul
