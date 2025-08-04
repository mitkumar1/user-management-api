@echo off
REM Full Monitoring Stack Test Script
REM Tests User Management API with Grafana, Prometheus, and full monitoring

echo 🌟 User Management API - Full Monitoring Stack Test
echo ====================================================
echo.

REM Check prerequisites
echo Checking prerequisites...
echo ------------------------

docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker not found. Please install Docker Desktop
    echo Download from: https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)
echo ✅ Docker is available

java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java not found. Please install Java 21
    pause
    exit /b 1
)
echo ✅ Java is available

curl --version >nul 2>&1
if errorlevel 1 (
    echo ❌ curl not found. Please install curl
    pause
    exit /b 1
)
echo ✅ curl is available

echo.

echo Step 1: Starting Full Monitoring Stack
echo --------------------------------------
echo This will start:
echo - PostgreSQL database
echo - Prometheus (metrics collection)
echo - Grafana (visualization)
echo - SonarQube (code quality)
echo - pgAdmin (database management)
echo - Your User Management API
echo.

echo Starting monitoring stack...
docker-compose -f docker-compose.monitoring.yml up -d

if errorlevel 1 (
    echo ❌ Failed to start monitoring stack
    echo Please check Docker Desktop is running
    pause
    exit /b 1
)

echo ✅ Monitoring stack started
echo.

echo Step 2: Waiting for Services to Initialize
echo ------------------------------------------
echo.

echo Waiting for services to start (this may take 2-3 minutes)...
timeout /t 30 /nobreak >nul

REM Check each service
echo Checking service status...

:check_api
echo Checking API...
curl -s http://localhost:8080/actuator/health >nul 2>&1
if errorlevel 1 (
    echo ⏳ API starting... (waiting 10 seconds)
    timeout /t 10 /nobreak >nul
    goto check_api
)
echo ✅ API is running

:check_prometheus
echo Checking Prometheus...
curl -s http://localhost:9090/-/healthy >nul 2>&1
if errorlevel 1 (
    echo ⏳ Prometheus starting... (waiting 5 seconds)
    timeout /t 5 /nobreak >nul
    goto check_prometheus
)
echo ✅ Prometheus is running

:check_grafana
echo Checking Grafana...
curl -s http://localhost:3000/api/health >nul 2>&1
if errorlevel 1 (
    echo ⏳ Grafana starting... (waiting 5 seconds)
    timeout /t 5 /nobreak >nul
    goto check_grafana
)
echo ✅ Grafana is running

echo.
echo Step 3: Service URLs
echo --------------------
echo 🌐 Your API:          http://localhost:8080
echo 📊 Grafana:           http://localhost:3000 (admin/admin)
echo 📈 Prometheus:        http://localhost:9090
echo 🔍 SonarQube:         http://localhost:9000 (admin/admin)
echo 🗄️  pgAdmin:           http://localhost:8082 (admin@example.com/admin123)
echo 📋 API Health:        http://localhost:8080/actuator/health
echo 📊 API Metrics:       http://localhost:8080/actuator/metrics
echo.

echo Step 4: Opening Monitoring Dashboards
echo -------------------------------------
echo.

echo Opening key dashboards in your browser...
start http://localhost:3000
start http://localhost:9090
start http://localhost:8080/actuator/health

echo ✅ Dashboards opened
echo.

echo Step 5: Running Comprehensive Load Test
echo ---------------------------------------
echo.

echo This will generate realistic load to test monitoring:
echo - 20 concurrent users
echo - 5-minute duration
echo - Mixed API operations (register, login, profile access)
echo.

echo Starting load test...
call test-scripts\load-test.bat 20 300 0.5

echo.
echo Step 6: Grafana Dashboard Setup
echo ------------------------------
echo.

echo To set up custom dashboards in Grafana:
echo.
echo 1. Go to http://localhost:3000
echo 2. Login with admin/admin
echo 3. Import dashboard from monitoring/grafana/dashboards/api-performance-dashboard.json
echo 4. Or create custom dashboard with these queries:
echo.
echo Key Prometheus Queries:
echo -----------------------
echo Request Rate:     rate(http_server_requests_seconds_count[5m])
echo Response Time:    histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))
echo Error Rate:       rate(http_server_requests_seconds_count{status=~"4..|5.."}[5m])
echo Memory Usage:     jvm_memory_used_bytes{area="heap"}
echo CPU Usage:        process_cpu_usage
echo.

echo Step 7: AppDynamics Integration (Optional)
echo -----------------------------------------
echo.

echo To integrate with AppDynamics:
echo 1. Sign up for free trial: https://www.appdynamics.com/free-trial/
echo 2. Download Java agent
echo 3. Place agent in monitoring/appd/ directory
echo 4. Update docker-compose.monitoring.yml with your credentials
echo 5. Restart stack: docker-compose -f docker-compose.monitoring.yml restart
echo.

echo Step 8: Dynatrace Integration (Optional)
echo ----------------------------------------
echo.

echo To integrate with Dynatrace:
echo 1. Sign up for free trial: https://www.dynatrace.com/trial/
echo 2. Download OneAgent
echo 3. Add to Dockerfile or as environment variable
echo 4. Restart stack with Dynatrace configuration
echo.

echo Step 9: Performance Testing Scenarios
echo -------------------------------------
echo.

echo Run different test scenarios:
echo.
echo Light Load:    call test-scripts\load-test.bat 5 120 1
echo Medium Load:   call test-scripts\load-test.bat 15 300 0.5  
echo Heavy Load:    call test-scripts\load-test.bat 50 600 0.1
echo Stress Test:   call test-scripts\load-test.bat 100 900 0.05
echo.

echo Step 10: Monitoring Best Practices
echo ----------------------------------
echo.

echo Key metrics to monitor:
echo ✅ Response time percentiles (50th, 90th, 95th, 99th)
echo ✅ Request rate and throughput
echo ✅ Error rate and types
echo ✅ Database connection pool utilization
echo ✅ JVM memory and garbage collection
echo ✅ CPU and system resources
echo ✅ Custom business metrics
echo.

echo Alerting recommendations:
echo 🚨 Response time > 1 second (95th percentile)
echo 🚨 Error rate > 1%%
echo 🚨 Memory usage > 85%%
echo 🚨 Database connections > 80%% of pool
echo 🚨 CPU usage > 80%%
echo.

echo 🎉 Full Monitoring Stack Setup Complete!
echo ========================================
echo.

echo Your monitoring environment is ready:
echo.
echo 📊 Monitor performance in Grafana: http://localhost:3000
echo 📈 View raw metrics in Prometheus: http://localhost:9090  
echo 🔍 Analyze code quality in SonarQube: http://localhost:9000
echo 🗄️  Manage database with pgAdmin: http://localhost:8082
echo 🌐 Test API endpoints: http://localhost:8080
echo.

echo To stop the monitoring stack:
echo docker-compose -f docker-compose.monitoring.yml down
echo.

echo To view logs:
echo docker-compose -f docker-compose.monitoring.yml logs -f [service-name]
echo.

echo Press any key to exit...
pause >nul
