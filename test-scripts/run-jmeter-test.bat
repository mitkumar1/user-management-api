@echo off
REM JMeter Load Testing Script for User Management API
REM This script runs comprehensive load tests using Apache JMeter
REM Usage: run-jmeter-test.bat [users] [ramp_time] [duration]

echo ====================================================
echo 🚀 JMeter Load Testing - User Management API
echo ====================================================

REM Set default values
set USERS=%1
set RAMP_TIME=%2
set DURATION=%3

if "%USERS%"=="" set USERS=10
if "%RAMP_TIME%"=="" set RAMP_TIME=30
if "%DURATION%"=="" set DURATION=120

REM Configuration
set JMETER_HOME=C:\apache-jmeter-5.6.2
set TEST_PLAN=jmeter-load-test.jmx
set RESULTS_FILE=jmeter-results-%date:~-4,4%%date:~-10,2%%date:~-7,2%-%time:~0,2%%time:~3,2%%time:~6,2%.jtl
set REPORT_DIR=jmeter-report-%date:~-4,4%%date:~-10,2%%date:~-7,2%-%time:~0,2%%time:~3,2%%time:~6,2%
set LOG_FILE=jmeter-test-%date:~-4,4%%date:~-10,2%%date:~-7,2%-%time:~0,2%%time:~3,2%%time:~6,2%.log

echo 📊 Test Parameters:
echo    Users: %USERS%
echo    Ramp Time: %RAMP_TIME% seconds  
echo    Duration: %DURATION% seconds
echo    Results: %RESULTS_FILE%
echo    Report: %REPORT_DIR%
echo.

REM Check if JMeter is installed
if not exist "%JMETER_HOME%\bin\jmeter.bat" (
    echo ❌ JMeter not found at %JMETER_HOME%
    echo 📥 Installing JMeter...
    call :install_jmeter
    if errorlevel 1 (
        echo ❌ Failed to install JMeter
        pause
        exit /b 1
    )
)

REM Check if API is running
echo 🔍 Checking if API is running...
curl -s http://localhost:8080/actuator/health > nul 2>&1
if errorlevel 1 (
    echo ❌ API not accessible at http://localhost:8080
    echo 🚀 Please start the application first:
    echo    mvn spring-boot:run
    pause
    exit /b 1
)

echo ✅ API is running and accessible

REM Create results directory
if not exist "results" mkdir results

REM Run JMeter test
echo.
echo 🧪 Starting JMeter Load Test...
echo ⏰ Test will run for %DURATION% seconds with %USERS% users
echo.

"%JMETER_HOME%\bin\jmeter.bat" ^
    -n ^
    -t "%TEST_PLAN%" ^
    -l "results\%RESULTS_FILE%" ^
    -e ^
    -o "results\%REPORT_DIR%" ^
    -JUSERS=%USERS% ^
    -JRAMP_TIME=%RAMP_TIME% ^
    -JDURATION=%DURATION% ^
    -JBASE_URL=http://localhost:8080 ^
    -j "results\%LOG_FILE%"

if errorlevel 1 (
    echo ❌ JMeter test failed
    echo 📋 Check log file: results\%LOG_FILE%
    pause
    exit /b 1
)

echo.
echo ✅ JMeter test completed successfully!
echo.
echo 📊 Results:
echo    📄 Raw Results: results\%RESULTS_FILE%
echo    📈 HTML Report: results\%REPORT_DIR%\index.html
echo    📋 Log File: results\%LOG_FILE%
echo.

REM Display summary
echo 📋 Test Summary:
echo ===============
findstr "summary" "results\%LOG_FILE%"

echo.
echo 🌐 Opening HTML report...
start "" "results\%REPORT_DIR%\index.html"

echo.
echo 📊 Monitor real-time metrics at:
echo    Grafana: http://localhost:3000
echo    Prometheus: http://localhost:9090  
echo    Application: http://localhost:8080/actuator/metrics
echo.

pause
exit /b 0

:install_jmeter
echo 📥 Downloading and installing JMeter...
if not exist "C:\apache-jmeter-5.6.2" (
    echo Downloading JMeter 5.6.2...
    powershell -Command "& {Invoke-WebRequest -Uri 'https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.2.zip' -OutFile 'jmeter.zip'}"
    
    echo Extracting JMeter...
    powershell -Command "& {Expand-Archive -Path 'jmeter.zip' -DestinationPath 'C:\' -Force}"
    
    del jmeter.zip
    
    echo ✅ JMeter installed successfully at C:\apache-jmeter-5.6.2
) else (
    echo ✅ JMeter already installed
)
exit /b 0
