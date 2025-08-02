@echo off
REM PostgreSQL Setup Script for Windows
REM This script helps set up PostgreSQL for the User Management API

setlocal enabledelayedexpansion

echo 🐘 PostgreSQL Setup for User Management API
echo.

REM Check if PostgreSQL is already installed
where psql >nul 2>&1
if %errorlevel% == 0 (
    echo ✅ PostgreSQL is already installed
    goto :setup_database
) else (
    echo ❌ PostgreSQL is not installed
    echo.
    echo 📥 Please install PostgreSQL first:
    echo    1. Download from: https://www.postgresql.org/download/windows/
    echo    2. Run the installer as Administrator
    echo    3. Choose these settings:
    echo       - Installation Directory: C:\Program Files\PostgreSQL\15
    echo       - Data Directory: C:\Program Files\PostgreSQL\15\data
    echo       - Port: 5432
    echo       - Superuser password: SecureP@ssw0rd123
    echo    4. Add to PATH: C:\Program Files\PostgreSQL\15\bin
    echo.
    pause
    goto :end
)

:setup_database
echo.
echo 🔧 Setting up database...

REM Check if PostgreSQL service is running
sc query postgresql-x64-15 | find "RUNNING" >nul
if %errorlevel% == 0 (
    echo ✅ PostgreSQL service is running
) else (
    echo ⚠️  PostgreSQL service is not running, attempting to start...
    net start postgresql-x64-15
    if %errorlevel% == 0 (
        echo ✅ PostgreSQL service started
    ) else (
        echo ❌ Failed to start PostgreSQL service
        echo    Please start it manually from Services or run:
        echo    net start postgresql-x64-15
        pause
        goto :end
    )
)

echo.
echo 📊 Creating database and user...
echo.

REM Create database setup SQL file
set SETUP_SQL=%TEMP%\setup_userdb.sql
echo -- User Management Database Setup > "%SETUP_SQL%"
echo. >> "%SETUP_SQL%"
echo -- Create database >> "%SETUP_SQL%"
echo CREATE DATABASE userdb; >> "%SETUP_SQL%"
echo. >> "%SETUP_SQL%"
echo -- Create user >> "%SETUP_SQL%"
echo CREATE USER dbuser WITH ENCRYPTED PASSWORD 'SecureP@ssw0rd123'; >> "%SETUP_SQL%"
echo. >> "%SETUP_SQL%"
echo -- Grant privileges >> "%SETUP_SQL%"
echo GRANT ALL PRIVILEGES ON DATABASE userdb TO dbuser; >> "%SETUP_SQL%"
echo. >> "%SETUP_SQL%"
echo -- Connect to userdb and grant schema privileges >> "%SETUP_SQL%"
echo \c userdb; >> "%SETUP_SQL%"
echo GRANT ALL ON SCHEMA public TO dbuser; >> "%SETUP_SQL%"
echo GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO dbuser; >> "%SETUP_SQL%"
echo GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO dbuser; >> "%SETUP_SQL%"

echo 🔑 Running database setup (you may be prompted for postgres password)...
psql -U postgres -h localhost -f "%SETUP_SQL%"

if %errorlevel% == 0 (
    echo ✅ Database setup completed successfully
) else (
    echo ❌ Database setup failed
    echo    Please check your PostgreSQL installation and password
    echo    Default superuser: postgres
    echo    Default password: SecureP@ssw0rd123
)

REM Clean up temporary file
del "%SETUP_SQL%" 2>nul

echo.
echo 📋 Running database initialization...
echo 🔑 Initializing schema (you may be prompted for dbuser password: SecureP@ssw0rd123)...

REM Run the initialization script
if exist "database\init.sql" (
    psql -U dbuser -h localhost -d userdb -f "database\init.sql"
    if %errorlevel% == 0 (
        echo ✅ Database initialization completed
    ) else (
        echo ❌ Database initialization failed
    )
) else (
    echo ⚠️  Database initialization script not found: database\init.sql
)

echo.
echo 🧪 Testing database connection...
psql -U dbuser -h localhost -d userdb -c "SELECT 'Connection successful!' as status;"

if %errorlevel% == 0 (
    echo ✅ Database connection test passed
) else (
    echo ❌ Database connection test failed
)

echo.
echo 📊 Database Information:
echo    Host: localhost
echo    Port: 5432
echo    Database: userdb
echo    Username: dbuser
echo    Password: SecureP@ssw0rd123
echo.
echo 🌐 pgAdmin Access (if installed):
echo    URL: http://localhost:8082
echo    Email: admin@example.com
echo    Password: admin123
echo.
echo 🚀 Next Steps:
echo    1. Start the application: mvn spring-boot:run
echo    2. Or with monitoring: docker-compose -f docker-compose.monitoring.yml up
echo    3. Access application: http://localhost:8080
echo    4. Check health: http://localhost:8080/actuator/health
echo.

:end
echo 🎉 PostgreSQL setup completed!
pause
