@echo off
REM Load Testing Script for User Management API (Windows version)
REM Tests authentication, user operations, and monitoring

setlocal enabledelayedexpansion

set BASE_URL=http://localhost:8080
set USERS=%1
set DURATION=%2
set DELAY=%3

REM Set defaults if not provided
if "%USERS%"=="" set USERS=10
if "%DURATION%"=="" set DURATION=60
if "%DELAY%"=="" set DELAY=1

echo 🚀 Starting Load Test for User Management API
echo =============================================
echo Target URL: %BASE_URL%
echo Concurrent Users: %USERS%
echo Duration: %DURATION% seconds
echo Request Delay: %DELAY% seconds
echo.

REM Create unique test prefix
for /f "tokens=1-4 delims=/ " %%a in ('date /t') do set current_date=%%a%%b%%c
for /f "tokens=1-2 delims=: " %%a in ('time /t') do set current_time=%%a%%b
set test_prefix=loadtest_%current_date%_%current_time%

REM Log file
set log_file=load_test_%current_date%_%current_time%.log

echo 📝 Logging to: %log_file%
echo.

REM Initialize counters
set total_requests=0
set successful_requests=0
set failed_requests=0

REM Check if API is accessible
echo 🔍 Checking API accessibility...
curl -s --max-time 5 "%BASE_URL%/actuator/health" >nul 2>&1
if errorlevel 1 (
    echo ❌ API is not accessible at %BASE_URL%
    echo Please ensure the application is running:
    echo   mvn spring-boot:run
    echo   or
    echo   docker-compose -f docker-compose.monitoring.yml up -d
    exit /b 1
)

echo ✅ API is accessible
echo.

echo Progress Legend:
echo . = Health check success    E = Health check error
echo + = Registration success    R = Registration error  
echo L = Login success          l = Login error
echo P = Profile success        p = Profile error
echo M = Metrics success        m = Metrics error
echo.

REM Log start
echo [%date% %time%] Starting load test... >> %log_file%

REM Calculate end time
set /a end_time=%DURATION%
set request_counter=0
set user_counter=1

REM Main test loop
for /l %%t in (1,1,%end_time%) do (
    for /l %%u in (1,1,%USERS%) do (
        set /a current_user=!user_counter! + %%u
        set /a test_type=!request_counter! %% 6
        
        REM Different test types
        if !test_type! equ 0 (
            call :test_health !current_user!
        ) else if !test_type! equ 1 (
            call :test_registration !current_user!
        ) else if !test_type! equ 2 (
            call :test_login !current_user!
        ) else if !test_type! equ 3 (
            call :test_metrics
        ) else (
            call :test_health !current_user!
        )
        
        set /a request_counter=!request_counter! + 1
        timeout /t %DELAY% /nobreak >nul 2>&1
    )
    
    set /a user_counter=!user_counter! + %USERS%
    
    REM Display progress every 10 seconds
    set /a progress_check=%%t %% 10
    if !progress_check! equ 0 (
        set /a remaining=%end_time% - %%t
        echo  [%%t s elapsed, !remaining! s remaining]
    )
)

echo.
echo.
echo 📊 Load Test Results
echo ===================
echo Total Requests: %total_requests%
echo Successful Requests: %successful_requests%
echo Failed Requests: %failed_requests%

if %total_requests% gtr 0 (
    set /a success_rate=(%successful_requests% * 100) / %total_requests%
    echo Success Rate: !success_rate!%%
    
    if !success_rate! geq 95 (
        echo Status: ✅ EXCELLENT
    ) else if !success_rate! geq 90 (
        echo Status: ⚠️  GOOD
    ) else (
        echo Status: ❌ NEEDS ATTENTION
    )
)

echo.
echo 📈 Check monitoring dashboards:
echo - Grafana: http://localhost:3000
echo - Prometheus: http://localhost:9090
echo - Application Health: %BASE_URL%/actuator/health
echo.

REM Log final stats
echo === FINAL STATISTICS === >> %log_file%
echo Total Requests: %total_requests% >> %log_file%
echo Successful Requests: %successful_requests% >> %log_file%
echo Failed Requests: %failed_requests% >> %log_file%
echo Duration: %DURATION% seconds >> %log_file%
echo Users: %USERS% >> %log_file%
echo ======================== >> %log_file%

echo 📁 Complete log saved to: %log_file%
goto :end

:test_health
curl -s -w "%%{http_code}" -o nul "%BASE_URL%/actuator/health" >temp_response.txt 2>&1
set /p response=<temp_response.txt
del temp_response.txt >nul 2>&1

set /a total_requests=%total_requests% + 1

if "%response%"=="200" (
    set /a successful_requests=%successful_requests% + 1
    echo | set /p="."
) else (
    set /a failed_requests=%failed_requests% + 1
    echo | set /p="E"
)
goto :eof

:test_registration
set username=%test_prefix%_user_%1

curl -s -w "%%{http_code}" -o nul -X POST "%BASE_URL%/api/auth/signup" -H "Content-Type: application/json" -d "{\"name\": \"Test User %1\", \"username\": \"%username%\", \"email\": \"%username%@loadtest.com\", \"password\": \"Test123!@#\"}" >temp_response.txt 2>&1

set /p response=<temp_response.txt
del temp_response.txt >nul 2>&1

set /a total_requests=%total_requests% + 1

if "%response%"=="200" (
    set /a successful_requests=%successful_requests% + 1
    echo | set /p="+"
) else if "%response%"=="201" (
    set /a successful_requests=%successful_requests% + 1
    echo | set /p="+"
) else (
    set /a failed_requests=%failed_requests% + 1
    echo | set /p="R"
)
goto :eof

:test_login
set username=%test_prefix%_user_%1

curl -s -X POST "%BASE_URL%/api/auth/signin" -H "Content-Type: application/json" -d "{\"usernameOrEmail\": \"%username%\", \"password\": \"Test123!@#\"}" >temp_login.txt 2>&1

set /a total_requests=%total_requests% + 1

findstr "accessToken" temp_login.txt >nul 2>&1
if !errorlevel! equ 0 (
    set /a successful_requests=%successful_requests% + 1
    echo | set /p="L"
) else (
    set /a failed_requests=%failed_requests% + 1
    echo | set /p="l"
)

del temp_login.txt >nul 2>&1
goto :eof

:test_metrics
curl -s -w "%%{http_code}" -o nul "%BASE_URL%/actuator/metrics" >temp_response.txt 2>&1
set /p response=<temp_response.txt
del temp_response.txt >nul 2>&1

set /a total_requests=%total_requests% + 1

if "%response%"=="200" (
    set /a successful_requests=%successful_requests% + 1
    echo | set /p="M"
) else (
    set /a failed_requests=%failed_requests% + 1
    echo | set /p="m"
)
goto :eof

:end
endlocal
