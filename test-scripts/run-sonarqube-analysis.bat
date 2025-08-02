@echo off
REM SonarQube Analysis Script for User Management API
REM This script runs comprehensive code quality analysis using SonarQube
REM Usage: run-sonarqube-analysis.bat

echo ====================================================
echo 🔍 SonarQube Analysis - User Management API
echo ====================================================

REM Configuration
set SONAR_HOST_URL=http://localhost:9000
set SONAR_LOGIN=admin
set SONAR_PASSWORD=admin
set PROJECT_KEY=user-management-api
set PROJECT_NAME="User Management API"
set PROJECT_VERSION=1.0.0

echo 📊 SonarQube Configuration:
echo    Host URL: %SONAR_HOST_URL%
echo    Project Key: %PROJECT_KEY%
echo    Project Name: %PROJECT_NAME%
echo    Version: %PROJECT_VERSION%
echo.

REM Check if Maven is available
where mvn >nul 2>nul
if errorlevel 1 (
    echo ❌ Maven not found in PATH
    echo 📥 Please install Maven or add it to PATH
    pause
    exit /b 1
)

echo ✅ Maven found

REM Check if SonarQube is running
echo 🔍 Checking if SonarQube is running...
curl -s %SONAR_HOST_URL%/api/system/status > nul 2>&1
if errorlevel 1 (
    echo ❌ SonarQube not accessible at %SONAR_HOST_URL%
    echo 🚀 Starting SonarQube with Docker...
    call :start_sonarqube
    if errorlevel 1 (
        echo ❌ Failed to start SonarQube
        pause
        exit /b 1
    )
)

echo ✅ SonarQube is running

REM Wait for SonarQube to be fully ready
echo 🕐 Waiting for SonarQube to be ready...
:wait_loop
curl -s %SONAR_HOST_URL%/api/system/status | findstr "UP" > nul
if errorlevel 1 (
    echo    Waiting for SonarQube...
    timeout /t 5 > nul
    goto wait_loop
)

echo ✅ SonarQube is ready

REM Create project if it doesn't exist
echo 🔧 Setting up SonarQube project...
curl -s -u %SONAR_LOGIN%:%SONAR_PASSWORD% ^
    -X POST "%SONAR_HOST_URL%/api/projects/create" ^
    -d "project=%PROJECT_KEY%&name=%PROJECT_NAME%" > nul 2>&1

REM Run tests with coverage
echo.
echo 🧪 Running tests with coverage...
mvn clean test jacoco:report

if errorlevel 1 (
    echo ❌ Tests failed
    echo 📋 Please fix test failures before running SonarQube analysis
    pause
    exit /b 1
)

echo ✅ Tests completed successfully

REM Run SonarQube analysis
echo.
echo 🔍 Running SonarQube analysis...
mvn sonar:sonar ^
    -Dsonar.projectKey=%PROJECT_KEY% ^
    -Dsonar.projectName=%PROJECT_NAME% ^
    -Dsonar.projectVersion=%PROJECT_VERSION% ^
    -Dsonar.host.url=%SONAR_HOST_URL% ^
    -Dsonar.login=%SONAR_LOGIN% ^
    -Dsonar.password=%SONAR_PASSWORD% ^
    -Dsonar.java.coveragePlugin=jacoco ^
    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
    -Dsonar.junit.reportPaths=target/surefire-reports ^
    -Dsonar.sources=src/main/java ^
    -Dsonar.tests=src/test/java ^
    -Dsonar.java.binaries=target/classes ^
    -Dsonar.java.test.binaries=target/test-classes

if errorlevel 1 (
    echo ❌ SonarQube analysis failed
    echo 📋 Check Maven output for details
    pause
    exit /b 1
)

echo.
echo ✅ SonarQube analysis completed successfully!
echo.

REM Get analysis results
echo 📊 Analysis Results:
echo ===================
curl -s -u %SONAR_LOGIN%:%SONAR_PASSWORD% ^
    "%SONAR_HOST_URL%/api/measures/component?component=%PROJECT_KEY%&metricKeys=alert_status,bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density,ncloc,sqale_rating,reliability_rating,security_rating"

echo.
echo.
echo 🌐 View detailed results at:
echo    %SONAR_HOST_URL%/dashboard?id=%PROJECT_KEY%
echo.

REM Open SonarQube dashboard
echo 🌐 Opening SonarQube dashboard...
start "" "%SONAR_HOST_URL%/dashboard?id=%PROJECT_KEY%"

echo.
echo 📊 Key Quality Gates:
echo    🐛 Bugs: Should be 0
echo    🔒 Vulnerabilities: Should be 0  
echo    🧹 Code Smells: < 10
echo    📈 Coverage: > 80%%
echo    📋 Duplications: < 3%%
echo    ⭐ Rating: A or B
echo.

pause
exit /b 0

:start_sonarqube
echo 🚀 Starting SonarQube with Docker...
docker run -d --name sonarqube -p 9000:9000 sonarqube:community

if errorlevel 1 (
    echo ❌ Failed to start SonarQube container
    echo 📋 Make sure Docker is running
    return 1
)

echo ✅ SonarQube container started
echo 🕐 Waiting for SonarQube to initialize (this may take a few minutes)...

REM Wait for SonarQube to start
:startup_wait
timeout /t 10 > nul
curl -s %SONAR_HOST_URL%/api/system/status > nul 2>&1
if errorlevel 1 (
    echo    Still starting up...
    goto startup_wait
)

echo ✅ SonarQube started successfully
return 0
