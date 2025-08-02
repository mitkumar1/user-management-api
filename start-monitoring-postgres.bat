@echo off
REM Quick Start Script with PostgreSQL for Windows
REM Sets up PostgreSQL and starts the complete monitoring stack

setlocal enabledelayedexpansion

echo 🚀 User Management API - Quick Start with PostgreSQL
echo Setting up PostgreSQL and monitoring stack...

REM Check if Docker is available
where docker >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not installed. Please install Docker Desktop first.
    echo Download from: https://www.docker.com/products/docker-desktop/
    pause
    exit /b 1
)

where docker-compose >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Compose is not available. Please install Docker Desktop.
    pause
    exit /b 1
)

echo ✅ Docker and Docker Compose are available

REM Check if database directory exists
if not exist "database\init.sql" (
    echo ⚠️  Database initialization script not found
    echo Creating basic database structure...
    
    if not exist "database" mkdir database
    
    echo -- Basic User Management Database Schema > database\init.sql
    echo CREATE TABLE IF NOT EXISTS roles ( >> database\init.sql
    echo     id BIGSERIAL PRIMARY KEY, >> database\init.sql
    echo     name VARCHAR(60) NOT NULL UNIQUE, >> database\init.sql
    echo     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP >> database\init.sql
    echo ); >> database\init.sql
    echo. >> database\init.sql
    echo CREATE TABLE IF NOT EXISTS users ( >> database\init.sql
    echo     id BIGSERIAL PRIMARY KEY, >> database\init.sql
    echo     username VARCHAR(50) NOT NULL UNIQUE, >> database\init.sql
    echo     email VARCHAR(100) NOT NULL UNIQUE, >> database\init.sql
    echo     password VARCHAR(120) NOT NULL, >> database\init.sql
    echo     first_name VARCHAR(50), >> database\init.sql
    echo     last_name VARCHAR(50), >> database\init.sql
    echo     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, >> database\init.sql
    echo     is_active BOOLEAN DEFAULT true >> database\init.sql
    echo ); >> database\init.sql
    echo. >> database\init.sql
    echo CREATE TABLE IF NOT EXISTS user_roles ( >> database\init.sql
    echo     user_id BIGINT NOT NULL, >> database\init.sql
    echo     role_id BIGINT NOT NULL, >> database\init.sql
    echo     PRIMARY KEY (user_id, role_id), >> database\init.sql
    echo     FOREIGN KEY (user_id) REFERENCES users(id), >> database\init.sql
    echo     FOREIGN KEY (role_id) REFERENCES roles(id) >> database\init.sql
    echo ); >> database\init.sql
    echo. >> database\init.sql
    echo INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN') ON CONFLICT DO NOTHING; >> database\init.sql
    
    echo ✅ Database initialization script created
)

REM Start PostgreSQL and pgAdmin first
echo 🐘 Starting PostgreSQL database...
docker-compose -f docker-compose.monitoring.yml up -d postgres pgadmin

REM Wait for PostgreSQL to be ready
echo ⏳ Waiting for PostgreSQL to be ready...
for /l %%i in (1,1,30) do (
    docker exec postgres-prod pg_isready -U dbuser -d userdb >nul 2>&1
    if !errorlevel! == 0 (
        echo ✅ PostgreSQL is ready
        goto :postgres_ready
    )
    echo    Waiting... (%%i/30)
    timeout /t 2 >nul
)

echo ❌ PostgreSQL failed to start. Check logs: docker logs postgres-prod
pause
exit /b 1

:postgres_ready

REM Build the application if needed
if not exist "target\user-management-api-1.0.0-SNAPSHOT.jar" (
    echo 🔨 Building application...
    call mvn clean package -DskipTests
    if !errorlevel! neq 0 (
        echo ❌ Application build failed
        pause
        exit /b 1
    )
    echo ✅ Application built successfully
)

REM Start the complete monitoring stack
echo 📊 Starting complete monitoring stack...
docker-compose -f docker-compose.monitoring.yml up -d

REM Wait for services to be ready
echo ⏳ Waiting for services to start...
timeout /t 30 >nul

REM Display access information
echo.
echo 🎉 Setup completed! Access your services:
echo ┌─────────────────────────────────────────────────────────┐
echo │  Service     │  URL                    │  Credentials  │
echo ├─────────────────────────────────────────────────────────┤
echo │  Application │  http://localhost:8080  │  N/A          │
echo │  PostgreSQL  │  localhost:5432         │  dbuser/***   │
echo │  pgAdmin     │  http://localhost:8082  │  admin/admin123 │
echo │  Grafana     │  http://localhost:3000  │  admin/admin  │
echo │  Prometheus  │  http://localhost:9090  │  N/A          │
echo │  SonarQube   │  http://localhost:9000  │  admin/admin  │
echo └─────────────────────────────────────────────────────────┘
echo.

echo 📋 Useful commands:
echo View logs:      docker-compose -f docker-compose.monitoring.yml logs -f
echo Stop services:  docker-compose -f docker-compose.monitoring.yml down
echo Database backup: backup-postgres.bat
echo Check health:   curl http://localhost:8080/actuator/health

echo.
echo 🔍 Next steps:
echo 1. 📊 Open Grafana and import dashboards
echo 2. 🔍 Run SonarQube analysis: mvn sonar:sonar
echo 3. 🧪 Test the API endpoints
echo 4. 📈 Monitor application metrics

echo.
echo 🚀 User Management API with PostgreSQL is ready!

REM Open key services in browser
echo 🌐 Opening services in your browser...
start http://localhost:8080/actuator/health
start http://localhost:8082
start http://localhost:3000

pause
