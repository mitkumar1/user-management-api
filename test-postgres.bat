@echo off
REM PostgreSQL Connection Test Script
REM Tests database connectivity and basic operations

setlocal enabledelayedexpansion

echo 🧪 PostgreSQL Connection Test
echo ============================

REM Configuration
set DB_NAME=userdb
set DB_USER=dbuser
set DB_HOST=localhost
set DB_PORT=5432

echo Testing database connection...
echo Host: %DB_HOST%:%DB_PORT%
echo Database: %DB_NAME%
echo User: %DB_USER%
echo.

REM Test 1: Check if PostgreSQL is ready
echo 🔍 Test 1: PostgreSQL Service Status
pg_isready -h %DB_HOST% -p %DB_PORT% -U %DB_USER% >nul 2>&1
if %errorlevel% == 0 (
    echo ✅ PostgreSQL service is ready
) else (
    echo ❌ PostgreSQL service is not ready
    echo    Make sure PostgreSQL is running
    echo    Docker: docker compose -f docker-compose.monitoring.yml up -d postgres
    echo    Native: net start postgresql-x64-15
    goto :end
)

REM Test 2: Database connection
echo.
echo 🔍 Test 2: Database Connection
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -c "SELECT 'Connection successful!' as status;" 2>nul
if %errorlevel% == 0 (
    echo ✅ Database connection successful
) else (
    echo ❌ Database connection failed
    echo    Check credentials: %DB_USER% / SecureP@ssw0rd123
    echo    Run setup script: setup-postgres.bat
    goto :end
)

REM Test 3: Check tables
echo.
echo 🔍 Test 3: Database Schema
for /f %%i in ('psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -c "SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public';" 2^>nul') do set TABLE_COUNT=%%i

if defined TABLE_COUNT (
    if %TABLE_COUNT% geq 3 (
        echo ✅ Database schema exists (%TABLE_COUNT% tables)
    ) else (
        echo ⚠️  Database schema incomplete (%TABLE_COUNT% tables)
        echo    Expected: users, roles, user_roles tables
        echo    Run: psql -U %DB_USER% -d %DB_NAME% -f database\init.sql
    )
) else (
    echo ❌ Cannot check database schema
)

REM Test 4: Check specific tables
echo.
echo 🔍 Test 4: Required Tables
set REQUIRED_TABLES=users roles user_roles

for %%t in (%REQUIRED_TABLES%) do (
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -c "\d %%t" >nul 2>&1
    if !errorlevel! == 0 (
        echo ✅ Table '%%t' exists
    ) else (
        echo ❌ Table '%%t' missing
    )
)

REM Test 5: Check roles data
echo.
echo 🔍 Test 5: Default Roles
for /f %%i in ('psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -c "SELECT count(*) FROM roles;" 2^>nul') do set ROLE_COUNT=%%i

if defined ROLE_COUNT (
    if %ROLE_COUNT% geq 2 (
        echo ✅ Default roles exist (%ROLE_COUNT% roles)
        echo    Roles:
        psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -c "SELECT '  - ' || name as role FROM roles;" -t 2>nul
    ) else (
        echo ⚠️  Default roles missing (%ROLE_COUNT% roles)
        echo    Expected: ROLE_USER, ROLE_ADMIN
    )
) else (
    echo ❌ Cannot check roles table
)

REM Test 6: Performance test
echo.
echo 🔍 Test 6: Basic Performance
echo Measuring query performance...
for /f "tokens=2" %%i in ('psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -c "\timing on" -c "SELECT count(*) FROM information_schema.tables;" 2^>nul ^| findstr "Time:"') do set QUERY_TIME=%%i

if defined QUERY_TIME (
    echo ✅ Query execution time: %QUERY_TIME%
) else (
    echo ❌ Performance test failed
)

REM Test 7: Connection pool test
echo.
echo 🔍 Test 7: Multiple Connections
set CONNECTION_TEST=0
for /l %%i in (1,1,5) do (
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -c "SELECT %%i as connection_test;" >nul 2>&1
    if !errorlevel! == 0 (
        set /a CONNECTION_TEST+=1
    )
)

echo ✅ Successful connections: %CONNECTION_TEST%/5

REM Summary
echo.
echo 📊 Test Summary
echo ==============
echo Database: %DB_NAME%
echo Host: %DB_HOST%:%DB_PORT%
echo User: %DB_USER%
echo Tables: %TABLE_COUNT%
echo Roles: %ROLE_COUNT%
echo Connections: %CONNECTION_TEST%/5

REM Application configuration test
echo.
echo 🔍 Test 8: Application Configuration
if exist "src\main\resources\application.properties" (
    findstr "postgresql" src\main\resources\application.properties >nul
    if !errorlevel! == 0 (
        echo ✅ Application configured for PostgreSQL
    ) else (
        echo ⚠️  Application not configured for PostgreSQL
        echo    Check src\main\resources\application.properties
    )
) else (
    echo ⚠️  Application configuration file not found
)

REM Docker test (if using Docker)
echo.
echo 🔍 Test 9: Docker Container Status
docker ps --filter "name=postgres-prod" --format "table {{.Names}}\t{{.Status}}" 2>nul
if %errorlevel% == 0 (
    echo ✅ Docker container status shown above
) else (
    echo ℹ️  Not using Docker or Docker not available
)

echo.
echo 🎉 PostgreSQL test completed!
echo.
echo 💡 Next steps:
echo    1. If tests passed: mvn spring-boot:run
echo    2. If tests failed: Check error messages above
echo    3. For help: See POSTGRESQL-SETUP.md
echo.

:end
pause
