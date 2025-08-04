# 📊 Monitoring & Testing Guide
# User Management API with Grafana, AppDynamics, and Dynatrace

This guide shows you how to test and monitor your User Management API using the complete monitoring stack.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Quick Start - Monitoring Setup](#quick-start-monitoring-setup)
3. [Grafana Testing](#grafana-testing)
4. [AppDynamics Testing](#appdynamics-testing)
5. [Dynatrace Testing](#dynatrace-testing)
6. [Load Testing with Monitoring](#load-testing-with-monitoring)
7. [API Endpoint Testing](#api-endpoint-testing)
8. [Performance Monitoring](#performance-monitoring)
9. [Troubleshooting](#troubleshooting)

## 🔧 Prerequisites

### Required Software
- Docker Desktop (for monitoring stack)
- Java 21 (verified working)
- Maven 3.9.9 (verified working)
- curl or Postman (for API testing)
- Browser (for monitoring dashboards)

### Monitoring Accounts (Optional for Advanced Features)
- AppDynamics trial account: https://www.appdynamics.com/free-trial/
- Dynatrace trial account: https://www.dynatrace.com/trial/

## 🚀 Quick Start - Monitoring Setup

### Option A: Quick Test (H2 Database - No Docker Required)
```bash
# For immediate testing without Docker setup
quick-test.bat  # Windows
```

### Option B: Full Monitoring Stack (Docker Required)
```bash
# For complete monitoring with Grafana, Prometheus, PostgreSQL
test-monitoring-stack.bat  # Windows

# Or manually:
docker-compose -f docker-compose.monitoring.yml up -d
docker-compose -f docker-compose.monitoring.yml ps
```

### Step 1: Start the Complete Monitoring Stack

```bash
# Start all monitoring services
docker-compose -f docker-compose.monitoring.yml up -d

# Check all services are running
docker-compose -f docker-compose.monitoring.yml ps
```

### Step 2: Verify Services Are Running

```bash
# Check service health
curl http://localhost:8080/actuator/health    # Your API
curl http://localhost:9090/-/healthy          # Prometheus
curl http://localhost:3000/api/health         # Grafana
curl http://localhost:9000/api/system/status  # SonarQube
```

### Step 3: Access Monitoring Dashboards

| Service | URL | Default Credentials |
|---------|-----|-------------------|
| **Your API** | http://localhost:8080 | N/A |
| **Grafana** | http://localhost:3000 | admin / admin |
| **Prometheus** | http://localhost:9090 | N/A |
| **SonarQube** | http://localhost:9000 | admin / admin |
| **pgAdmin** | http://localhost:8082 | admin@example.com / admin123 |
| **cAdvisor** | http://localhost:8081 | N/A |

## 📊 Grafana Testing

### Initial Setup

1. **Login to Grafana**
   ```
   URL: http://localhost:3000
   Username: admin
   Password: admin (change on first login)
   ```

2. **Verify Data Sources**
   - Go to Configuration → Data Sources
   - Prometheus should be configured at `http://prometheus:9090`

3. **Import Dashboards**
   ```bash
   # Create custom dashboard for your API
   # Go to Dashboards → Import
   # Use dashboard ID: 1860 (Node Exporter Full)
   # Use dashboard ID: 193 (Docker monitoring)
   ```

### Testing Your API Metrics in Grafana

1. **Create Custom Dashboard**
   - Go to Dashboards → New Dashboard
   - Add Panel → Query

2. **Key Metrics to Monitor**

   **HTTP Request Metrics:**
   ```promql
   # Request rate
   rate(http_server_requests_seconds_count[5m])
   
   # Response time percentiles
   histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))
   
   # Error rate
   rate(http_server_requests_seconds_count{status=~"4.."|status=~"5.."}[5m])
   ```

   **Application Metrics:**
   ```promql
   # JVM memory usage
   jvm_memory_used_bytes{area="heap"}
   
   # Database connection pool
   hikaricp_connections_active
   
   # CPU usage
   process_cpu_usage
   ```

3. **Test by Generating Load**
   ```bash
   # Generate test traffic
   for i in {1..100}; do
     curl http://localhost:8080/actuator/health
     sleep 1
   done
   ```

### Custom Dashboard Configuration

Create a new file for Grafana dashboard:

```json
{
  "dashboard": {
    "id": null,
    "title": "User Management API Dashboard",
    "panels": [
      {
        "title": "Request Rate",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_server_requests_seconds_count[5m])",
            "refId": "A"
          }
        ]
      },
      {
        "title": "Response Time",
        "type": "graph",
        "targets": [
          {
            "expr": "histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))",
            "refId": "A"
          }
        ]
      }
    ]
  }
}
```

## 🔍 AppDynamics Testing

### Setup AppDynamics Agent

1. **Download AppDynamics Java Agent**
   ```bash
   # Create directory for AppDynamics
   mkdir -p monitoring/appd
   
   # Download agent (requires AppDynamics account)
   # Place javaagent.jar in monitoring/appd/
   ```

2. **Configure Agent**
   Create `monitoring/appd/controller-info.xml`:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <controller-info>
       <controller-host>your-controller.saas.appdynamics.com</controller-host>
       <controller-port>443</controller-port>
       <controller-ssl-enabled>true</controller-ssl-enabled>
       <account-name>your-account</account-name>
       <account-access-key>your-access-key</account-access-key>
       <application-name>UserManagementAPI</application-name>
       <tier-name>Backend</tier-name>
       <node-name>Node1</node-name>
   </controller-info>
   ```

3. **Start with AppDynamics**
   ```bash
   # Update docker-compose.monitoring.yml with your AppDynamics details
   docker-compose -f docker-compose.monitoring.yml up -d user-management-api
   ```

### Testing with AppDynamics

1. **View Application in Controller**
   - Login to AppDynamics Controller
   - Navigate to Applications → UserManagementAPI

2. **Generate Test Traffic**
   ```bash
   # Create realistic load
   ./test-scripts/load-test.sh
   ```

3. **Monitor Key Metrics**
   - Response Time
   - Throughput
   - Error Rate
   - Infrastructure metrics
   - Database performance

## 🚀 Dynatrace Testing

### Setup Dynatrace OneAgent

1. **Install Dynatrace OneAgent**
   ```bash
   # Download OneAgent from your Dynatrace environment
   # https://[your-environment].dynatrace.com
   
   # For Docker deployment, add to Dockerfile:
   # COPY dynatrace-oneagent.jar /opt/dynatrace/
   ```

2. **Configure Environment Variables**
   ```yaml
   # In docker-compose.monitoring.yml
   environment:
     - DT_TENANT=your-tenant-id
     - DT_TENANTTOKEN=your-tenant-token
     - DT_CONNECTION_POINT=https://your-environment.dynatrace.com/communication
   ```

3. **Enable Dynatrace Monitoring**
   ```bash
   # Restart with Dynatrace
   docker-compose -f docker-compose.monitoring.yml down
   docker-compose -f docker-compose.monitoring.yml up -d
   ```

### Testing with Dynatrace

1. **View in Dynatrace Console**
   - Login to Dynatrace
   - Navigate to Applications & Microservices

2. **Key Monitoring Areas**
   - Application performance
   - Real user monitoring
   - Synthetic monitoring
   - Infrastructure monitoring

## 🧪 Load Testing with Monitoring

### Create Load Testing Script

Create `test-scripts/load-test.sh`:
```bash
#!/bin/bash

# Load Testing Script for User Management API
# Tests authentication, user operations, and monitoring

BASE_URL="http://localhost:8080"
USERS=50
DURATION=300  # 5 minutes

echo "Starting load test with $USERS concurrent users for $DURATION seconds"

# Function to test user registration
test_registration() {
    local user_id=$1
    curl -X POST "$BASE_URL/api/auth/signup" \
        -H "Content-Type: application/json" \
        -d "{
            \"name\": \"User$user_id\",
            \"username\": \"user$user_id\",
            \"email\": \"user$user_id@test.com\",
            \"password\": \"password123\"
        }" &
}

# Function to test user login
test_login() {
    local user_id=$1
    curl -X POST "$BASE_URL/api/auth/signin" \
        -H "Content-Type: application/json" \
        -d "{
            \"usernameOrEmail\": \"user$user_id\",
            \"password\": \"password123\"
        }" &
}

# Function to test user profile
test_profile() {
    local token=$1
    curl -X GET "$BASE_URL/api/user/me" \
        -H "Authorization: Bearer $token" &
}

# Start load test
start_time=$(date +%s)
end_time=$((start_time + DURATION))

user_counter=1
while [ $(date +%s) -lt $end_time ]; do
    for i in $(seq 1 $USERS); do
        test_registration $((user_counter + i))
        test_login $((user_counter + i))
        
        # Health check
        curl -s "$BASE_URL/actuator/health" > /dev/null &
        
        # Metrics endpoint
        curl -s "$BASE_URL/actuator/metrics" > /dev/null &
    done
    
    user_counter=$((user_counter + USERS))
    sleep 5
done

wait
echo "Load test completed"
```

### Execute Load Test

```bash
# Make script executable
chmod +x test-scripts/load-test.sh

# Run load test
./test-scripts/load-test.sh

# Monitor in parallel
watch -n 1 'curl -s http://localhost:8080/actuator/metrics/http.server.requests | jq'
```

## 🔍 API Endpoint Testing

### Test Authentication Flow

```bash
# Test user registration
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'

# Test user login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "password123"
  }' | jq -r '.accessToken')

# Test authenticated endpoint
curl -X GET http://localhost:8080/api/user/me \
  -H "Authorization: Bearer $TOKEN"
```

### Test Performance with Different Loads

```bash
# Light load (1 req/sec)
for i in {1..60}; do curl -s http://localhost:8080/actuator/health > /dev/null; sleep 1; done &

# Medium load (10 req/sec)
for i in {1..600}; do curl -s http://localhost:8080/actuator/health > /dev/null; sleep 0.1; done &

# Heavy load (100 req/sec)
for i in {1..6000}; do curl -s http://localhost:8080/actuator/health > /dev/null; sleep 0.01; done &
```

## 📈 Performance Monitoring

### Key Metrics to Monitor

1. **Application Metrics**
   - Response time (avg, 95th percentile)
   - Throughput (requests/second)
   - Error rate (%)
   - Memory usage
   - CPU utilization

2. **Database Metrics**
   - Connection pool usage
   - Query execution time
   - Database connections
   - Lock contention

3. **Infrastructure Metrics**
   - Container resource usage
   - Network I/O
   - Disk I/O
   - System load

### Create Monitoring Alerts

```bash
# Prometheus alert rules (monitoring/alerts.yml)
groups:
  - name: api-alerts
    rules:
      - alert: HighResponseTime
        expr: histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m])) > 1
        for: 2m
        labels:
          severity: warning
        annotations:
          summary: "High response time detected"
      
      - alert: HighErrorRate
        expr: rate(http_server_requests_seconds_count{status=~"5.."}[5m]) > 0.1
        for: 1m
        labels:
          severity: critical
        annotations:
          summary: "High error rate detected"
```

## 🐛 Troubleshooting

### Common Issues

1. **Monitoring Stack Not Starting**
   ```bash
   # Check Docker resources
   docker system info
   
   # Check logs
   docker-compose -f docker-compose.monitoring.yml logs
   
   # Restart services
   docker-compose -f docker-compose.monitoring.yml restart
   ```

2. **Metrics Not Appearing**
   ```bash
   # Check actuator endpoints
   curl http://localhost:8080/actuator
   curl http://localhost:8080/actuator/prometheus
   
   # Verify Prometheus targets
   curl http://localhost:9090/api/v1/targets
   ```

3. **Dashboard Not Loading**
   ```bash
   # Check Grafana logs
   docker logs grafana
   
   # Verify data source connection
   curl http://localhost:3000/api/datasources
   ```

### Performance Optimization

1. **JVM Tuning**
   ```bash
   # Add to JAVA_OPTS in docker-compose
   -Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200
   ```

2. **Database Optimization**
   ```bash
   # Monitor slow queries
   # Check connection pool settings
   # Analyze query performance
   ```

## 📊 Sample Test Scenarios

### Scenario 1: Normal Operations
```bash
# 30 users, 5-minute test
./test-scripts/load-test.sh 30 300
```

### Scenario 2: Peak Load
```bash
# 100 users, 10-minute test
./test-scripts/load-test.sh 100 600
```

### Scenario 3: Stress Test
```bash
# 200 users, 15-minute test
./test-scripts/load-test.sh 200 900
```

## 🎯 Success Criteria

Your monitoring setup is successful when you can:

1. ✅ View real-time metrics in Grafana
2. ✅ Track application performance in AppDynamics/Dynatrace
3. ✅ Monitor database performance
4. ✅ Detect and alert on performance issues
5. ✅ Analyze historical performance data
6. ✅ Correlate application and infrastructure metrics

## 🔗 Additional Resources

- [Grafana Documentation](https://grafana.com/docs/)
- [AppDynamics Java Agent](https://docs.appdynamics.com/latest/en/application-monitoring/install-app-server-agents/java-agent)
- [Dynatrace OneAgent](https://www.dynatrace.com/support/help/setup-and-configuration/dynatrace-oneagent)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Prometheus Metrics](https://prometheus.io/docs/concepts/metric_types/)

---

💡 **Pro Tip**: Start monitoring before running tests to establish baseline metrics!
