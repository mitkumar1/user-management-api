@echo off
REM Start PostgreSQL with native installation (no Docker required)
REM This script sets up PostgreSQL directly on Windows

echo User Management API - PostgreSQL Native Setup
echo =============================================

echo Checking for PostgreSQL installation...

REM Check if PostgreSQL is installed
where psql >nul 2>&1
if %errorlevel% == 0 (
    echo PostgreSQL is installed!
    psql --version
    goto :setup_database
) else (
    echo PostgreSQL is not installed.
    echo.
    echo Option 1: Install PostgreSQL manually
    echo    - Download from: https://www.postgresql.org/download/windows/
    echo    - Choose version 15 or later
    echo    - Set password: SecureP@ssw0rd123
    echo    - Port: 5432
    echo.
    echo Option 2: Install via package manager
    echo    - Chocolatey: choco install postgresql
    echo    - Winget: winget install PostgreSQL.PostgreSQL
    echo.
    set /p INSTALL_PG=Would you like to try installing PostgreSQL via Chocolatey? (y/N): 
    if /i "!INSTALL_PG!"=="y" (
        where choco >nul 2>&1
        if !errorlevel! == 0 (
            echo Installing PostgreSQL via Chocolatey...
            choco install postgresql -y
            echo PostgreSQL installation completed!
            echo Please restart this script after installation.
        ) else (
            echo Chocolatey is not installed. Please install PostgreSQL manually.
        )
    )
    pause
    exit /b 1
)

:setup_database
echo.
echo Setting up database...

REM Check if PostgreSQL service is running
sc query postgresql-x64-15 | find "RUNNING" >nul
if %errorlevel% == 0 (
    echo PostgreSQL service is running
) else (
    echo Starting PostgreSQL service...
    net start postgresql-x64-15 2>nul
    if %errorlevel% == 0 (
        echo PostgreSQL service started successfully
    ) else (
        echo Failed to start PostgreSQL service automatically
        echo Please start it manually from Services or run:
        echo    net start postgresql-x64-15
        pause
        exit /b 1
    )
)

REM Test connection
echo Testing PostgreSQL connection...
pg_isready -h localhost -p 5432 >nul 2>&1
if %errorlevel% == 0 (
    echo PostgreSQL is ready!
) else (
    echo PostgreSQL is not responding. Please check the installation.
    pause
    exit /b 1
)

REM Setup database if needed
echo.
echo Checking if userdb database exists...
psql -U postgres -h localhost -lqt | cut -d \| -f 1 | grep -qw userdb 2>nul
if %errorlevel% == 0 (
    echo Database 'userdb' already exists
) else (
    echo Creating database and user...
    echo You may be prompted for the postgres password: SecureP@ssw0rd123
    
    REM Create a temporary SQL file
    echo CREATE DATABASE userdb; > "%TEMP%\setup_db.sql"
    echo CREATE USER dbuser WITH ENCRYPTED PASSWORD 'SecureP@ssw0rd123'; >> "%TEMP%\setup_db.sql"
    echo GRANT ALL PRIVILEGES ON DATABASE userdb TO dbuser; >> "%TEMP%\setup_db.sql"
    
    psql -U postgres -h localhost -f "%TEMP%\setup_db.sql"
    del "%TEMP%\setup_db.sql"
    
    echo Database setup completed!
)

REM Initialize schema if needed
echo.
echo Initializing database schema...
if exist "database\init.sql" (
    echo Running database initialization...
    psql -U dbuser -h localhost -d userdb -f "database\init.sql"
    echo Schema initialization completed!
) else (
    echo Warning: database\init.sql not found. Creating basic schema...
    
    echo CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(50), email VARCHAR(100), password VARCHAR(120)); > "%TEMP%\basic_schema.sql"
    echo CREATE TABLE IF NOT EXISTS roles (id SERIAL PRIMARY KEY, name VARCHAR(60)); >> "%TEMP%\basic_schema.sql"
    echo INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN') ON CONFLICT DO NOTHING; >> "%TEMP%\basic_schema.sql"
    
    psql -U dbuser -h localhost -d userdb -f "%TEMP%\basic_schema.sql"
    del "%TEMP%\basic_schema.sql"
    
    echo Basic schema created!
)

REM Build and start the application
echo.
echo Setting up environment variables...
call setup-env-vars.bat

echo.
echo Building application...
call mvn clean package -DskipTests
if %errorlevel% == 0 (
    echo Build successful!
) else (
    echo Build failed. Please check the Maven output.
    pause
    exit /b 1
)

echo.
echo Starting User Management API...
echo ==============================
echo Database: PostgreSQL (localhost:5432/userdb)
echo Application: http://localhost:8080
echo Health Check: http://localhost:8080/actuator/health
echo.

REM Start the application with production profile
mvn spring-boot:run -Dspring.profiles.active=production

echo.
echo Application stopped.
pause
