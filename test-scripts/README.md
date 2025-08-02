# 🧪 Test Scripts Directory

This directory contains automated testing scripts for monitoring your User Management API with Grafana, AppDynamics, and Dynatrace.

## 📁 Available Scripts

### 🚀 Quick Testing Scripts

| Script | Platform | Purpose | Requirements |
|--------|----------|---------|-------------|
| `quick-test.bat` | Windows | Fast API testing with H2 database | Java, Maven |
| `test-monitoring-stack.bat` | Windows | Full monitoring stack testing | Docker, Java, Maven |

### 📊 Load Testing Scripts

| Script | Platform | Purpose | Usage |
|--------|----------|---------|-------|
| `load-test.bat` | Windows | Load testing with monitoring | `load-test.bat [users] [duration] [delay]` |
| `load-test.sh` | Linux/Mac | Load testing with monitoring | `./load-test.sh [users] [duration] [delay]` |

### 🎯 JMeter Load Testing Scripts

| Script | Platform | Purpose | Usage |
|--------|----------|---------|-------|
| `run-jmeter-test.bat` | Windows | Professional load testing with JMeter | `run-jmeter-test.bat [users] [ramp_time] [duration]` |
| `run-jmeter-test.sh` | Linux/Mac | Professional load testing with JMeter | `./run-jmeter-test.sh [users] [ramp_time] [duration]` |
| `jmeter-load-test.jmx` | All | JMeter test plan configuration | Use with JMeter GUI or scripts |

### 🔍 SonarQube Analysis Scripts

| Script | Platform | Purpose | Usage |
|--------|----------|---------|-------|
| `run-sonarqube-analysis.bat` | Windows | Code quality analysis with SonarQube | `run-sonarqube-analysis.bat` |
| `run-sonarqube-analysis.sh` | Linux/Mac | Code quality analysis with SonarQube | `./run-sonarqube-analysis.sh` |

## 🏃‍♂️ Quick Start

### 1. Immediate Testing (No Docker)
```bash
# Test API with H2 database
quick-test.bat
```

### 2. Full Monitoring Stack
```bash
# Test with Grafana, Prometheus, PostgreSQL
test-monitoring-stack.bat
```

### 3. Custom Load Testing
```bash
# Light load: 5 users, 60 seconds
load-test.bat 5 60 1

# Medium load: 20 users, 300 seconds  
load-test.bat 20 300 0.5

# Heavy load: 50 users, 600 seconds
load-test.bat 50 600 0.1
```

### 4. Professional JMeter Load Testing
```bash
# Windows - Professional load testing
run-jmeter-test.bat 50 60 300

# Linux/Mac - Professional load testing  
./run-jmeter-test.sh 50 60 300

# Parameters: [users] [ramp_time_seconds] [duration_seconds]
```

### 5. SonarQube Code Quality Analysis
```bash
# Windows - Full code quality analysis
run-sonarqube-analysis.bat

# Linux/Mac - Full code quality analysis
./run-sonarqube-analysis.sh

# Analyzes: bugs, vulnerabilities, code smells, coverage, duplications
```

## 📊 Load Testing Parameters

### Basic Load Testing Parameters
| Parameter | Description | Default | Example |
|-----------|-------------|---------|---------|
| `users` | Number of concurrent users | 10 | 20 |
| `duration` | Test duration in seconds | 60 | 300 |
| `delay` | Delay between requests (seconds) | 1 | 0.5 |

### JMeter Load Testing Parameters
| Parameter | Description | Default | Example |
|-----------|-------------|---------|---------|
| `users` | Number of concurrent virtual users | 10 | 50 |
| `ramp_time` | Time to reach full user load (seconds) | 30 | 60 |
| `duration` | Total test duration (seconds) | 120 | 300 |

### SonarQube Analysis Parameters
| Metric | Description | Target | Quality Gate |
|--------|-------------|--------|-------------|
| `bugs` | Number of bugs detected | 0 | A Rating |
| `vulnerabilities` | Security vulnerabilities | 0 | A Rating |
| `code_smells` | Code maintainability issues | < 10 | A/B Rating |
| `coverage` | Unit test code coverage | > 80% | Green |
| `duplications` | Duplicated code percentage | < 3% | Green |

## 🎯 Test Scenarios

### Scenario 1: Development Testing
```bash
# Quick validation during development
quick-test.bat
```

### Scenario 2: Performance Baseline
```bash
# Establish baseline metrics
load-test.bat 10 120 1
```

### Scenario 3: Load Testing
```bash
# Test under realistic load
load-test.bat 25 300 0.5
```

### Scenario 4: Stress Testing  
```bash
# Test system limits
load-test.bat 100 600 0.1
```

### Scenario 5: Professional Load Testing with JMeter
```bash
# Professional load testing with detailed reporting
run-jmeter-test.bat 50 60 300

# Generates: HTML reports, charts, detailed metrics
```

### Scenario 6: Code Quality Analysis
```bash
# Comprehensive code quality analysis
run-sonarqube-analysis.bat

# Analyzes: security, maintainability, reliability, coverage
```

### Scenario 7: Full CI/CD Pipeline Simulation
```bash
# 1. Code quality analysis
run-sonarqube-analysis.bat

# 2. Performance testing
run-jmeter-test.bat 30 30 180

# 3. Monitor results in Grafana
# http://localhost:3000
```

## 📈 Monitoring During Tests

### Real-time Monitoring URLs
- **API Health**: http://localhost:8080/actuator/health
- **API Metrics**: http://localhost:8080/actuator/metrics  
- **Grafana Dashboard**: http://localhost:3000 (admin/admin)
- **Prometheus Metrics**: http://localhost:9090
- **SonarQube Analysis**: http://localhost:9000 (admin/admin)
- **JMeter Results**: `results/jmeter-report-[timestamp]/index.html`

### Key Metrics to Watch
1. **Response Time**: Target < 500ms (95th percentile)
2. **Throughput**: Requests per second
3. **Error Rate**: Target < 1%
4. **Memory Usage**: JVM heap utilization
5. **Database Connections**: Pool utilization
6. **CPU Usage**: System resource consumption
7. **Code Quality**: SonarQube quality gates (A/B rating)
8. **Test Coverage**: > 80% code coverage

## 🔍 Test Output Interpretation

### Load Test Progress Indicators
```
. = Health check success    E = Health check error
+ = Registration success    R = Registration error  
L = Login success          l = Login error
P = Profile success        p = Profile error
M = Metrics success        m = Metrics error
```

### JMeter Test Progress Indicators
```
JMeter HTML Report Components:
📊 Dashboard: Overall test summary
📈 Over Time: Response times, throughput trends  
🎯 Response Times: Percentiles and distribution
📋 Summary: Request statistics and errors
❌ Errors: Failed requests analysis
📈 Statistics: Detailed metrics per endpoint
```

### SonarQube Quality Gates
```
Quality Gate Status:
✅ PASSED: All conditions met
❌ FAILED: One or more conditions failed

Key Quality Metrics:
🐛 Bugs: A (0 bugs) | B (1-10 bugs) | C (11+ bugs)
🔒 Vulnerabilities: A (0) | B (1-5) | C (6+ vulnerabilities)  
🧹 Code Smells: A (0-5) | B (6-10) | C (11+ code smells)
📈 Coverage: > 80% = Green | 70-80% = Yellow | < 70% = Red
📋 Duplications: < 3% = Green | 3-5% = Yellow | > 5% = Red
```

### Success Criteria
- ✅ **Excellent**: > 95% success rate, A rating in SonarQube
- ⚠️ **Good**: 90-95% success rate, B rating in SonarQube
- ❌ **Needs Attention**: < 90% success rate, C+ rating in SonarQube

## 🐛 Troubleshooting

### Common Issues

1. **API Not Accessible**
   ```bash
   # Check if application is running
   curl http://localhost:8080/actuator/health
   
   # Start application
   mvn spring-boot:run
   ```

2. **Docker Services Not Starting**
   ```bash
   # Check Docker status
   docker ps
   
   # Check logs
   docker-compose -f docker-compose.monitoring.yml logs
   ```

3. **Load Test Failing**
   ```bash
   # Check dependencies
   curl --version
   java -version
   
   # Verify API endpoints
   curl http://localhost:8080/api/auth/signup
   ```

4. **JMeter Test Issues**
   ```bash
   # Check JMeter installation
   jmeter --version
   
   # Verify test plan
   jmeter -t jmeter-load-test.jmx -l test-results.jtl -n
   
   # Check results
   cat results/jmeter-test-*.log
   ```

5. **SonarQube Analysis Issues**
   ```bash
   # Check SonarQube server
   curl http://localhost:9000/api/system/status
   
   # Verify Maven SonarQube plugin
   mvn help:describe -Dplugin=sonar
   
   # Check analysis logs
   cat target/sonar/report-task.txt
   ```

## 📁 Generated Files

### Log Files
- `load_test_YYYYMMDD_HHMMSS.log` - Detailed test execution logs
- `temp_*.txt` - Temporary response files (auto-deleted)

### JMeter Generated Files
- `jmeter-results-YYYYMMDD-HHMMSS.jtl` - Raw JMeter test results
- `jmeter-report-YYYYMMDD-HHMMSS/` - HTML report directory
- `jmeter-test-YYYYMMDD-HHMMSS.log` - JMeter execution logs

### SonarQube Generated Files
- `target/site/jacoco/jacoco.xml` - Code coverage report
- `target/surefire-reports/` - Test execution reports
- `target/sonar/report-task.txt` - SonarQube analysis task details

### Backup Files  
- `application.properties.backup` - Original configuration backup

## 🔧 Customization

### Modify Load Test Scenarios
Edit the test scripts to customize:
- API endpoints tested
- Request payloads
- Test user data
- Monitoring metrics collected

### Add Custom Metrics
Add custom Prometheus queries in Grafana:
```promql
# Custom application metrics
my_custom_metric_total
rate(my_custom_metric_total[5m])
```

### Customize JMeter Tests
Edit `jmeter-load-test.jmx` to customize:
- Thread groups and user scenarios
- Request parameters and payloads  
- Response assertions and validations
- Reporting and monitoring integration

### Customize SonarQube Rules
Add custom quality profiles in SonarQube:
- Custom coding standards
- Project-specific quality gates
- Integration with external tools
- Custom metrics and thresholds

## 📚 Related Documentation

- **Main Guide**: [`MONITORING-TESTING-GUIDE.md`](../MONITORING-TESTING-GUIDE.md)
- **PostgreSQL Setup**: [`POSTGRESQL-SETUP.md`](../POSTGRESQL-SETUP.md)
- **API Documentation**: [`README.md`](../README.md)

## 🎯 Best Practices

1. **Start Small**: Begin with quick-test.bat before full monitoring
2. **Baseline First**: Establish baseline metrics before optimization
3. **Monitor Continuously**: Keep Grafana open during tests
4. **Document Results**: Save test logs and screenshots
5. **Test Regularly**: Include in CI/CD pipeline
6. **Quality Gates**: Run SonarQube analysis before deployments
7. **Professional Testing**: Use JMeter for production-like load testing
8. **Comprehensive Coverage**: Combine functional, performance, and quality testing
9. **Automated Reporting**: Leverage HTML reports for stakeholder communication
10. **Continuous Improvement**: Use metrics to drive code and performance optimizations

---

💡 **Pro Tips**: 
- Run baseline tests before making code changes to measure performance impact!
- Use SonarQube quality gates to prevent technical debt accumulation!
- Combine JMeter load testing with Grafana monitoring for comprehensive insights!
- Set up automated quality and performance testing in your CI/CD pipeline!
