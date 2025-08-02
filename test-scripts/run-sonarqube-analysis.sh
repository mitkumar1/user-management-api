#!/bin/bash
# SonarQube Analysis Script for User Management API (Linux/Mac)
# This script runs comprehensive code quality analysis using SonarQube
# Usage: ./run-sonarqube-analysis.sh

echo "===================================================="
echo "🔍 SonarQube Analysis - User Management API"
echo "===================================================="

# Configuration
SONAR_HOST_URL="http://localhost:9000"
SONAR_LOGIN="admin"
SONAR_PASSWORD="admin"
PROJECT_KEY="user-management-api"
PROJECT_NAME="User Management API"
PROJECT_VERSION="1.0.0"

echo "📊 SonarQube Configuration:"
echo "   Host URL: $SONAR_HOST_URL"
echo "   Project Key: $PROJECT_KEY"
echo "   Project Name: $PROJECT_NAME"
echo "   Version: $PROJECT_VERSION"
echo ""

# Function to start SonarQube
start_sonarqube() {
    echo "🚀 Starting SonarQube with Docker..."
    docker run -d --name sonarqube -p 9000:9000 sonarqube:community
    
    if [ $? -ne 0 ]; then
        echo "❌ Failed to start SonarQube container"
        echo "📋 Make sure Docker is running"
        return 1
    fi
    
    echo "✅ SonarQube container started"
    echo "🕐 Waiting for SonarQube to initialize (this may take a few minutes)..."
    
    # Wait for SonarQube to start
    while true; do
        sleep 10
        if curl -s "$SONAR_HOST_URL/api/system/status" > /dev/null 2>&1; then
            break
        fi
        echo "   Still starting up..."
    done
    
    echo "✅ SonarQube started successfully"
    return 0
}

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found in PATH"
    echo "📥 Please install Maven first"
    exit 1
fi

echo "✅ Maven found"

# Check if SonarQube is running
echo "🔍 Checking if SonarQube is running..."
if ! curl -s "$SONAR_HOST_URL/api/system/status" > /dev/null 2>&1; then
    echo "❌ SonarQube not accessible at $SONAR_HOST_URL"
    echo "🚀 Starting SonarQube with Docker..."
    if ! start_sonarqube; then
        echo "❌ Failed to start SonarQube"
        exit 1
    fi
fi

echo "✅ SonarQube is running"

# Wait for SonarQube to be fully ready
echo "🕐 Waiting for SonarQube to be ready..."
while true; do
    if curl -s "$SONAR_HOST_URL/api/system/status" | grep -q "UP"; then
        break
    fi
    echo "   Waiting for SonarQube..."
    sleep 5
done

echo "✅ SonarQube is ready"

# Create project if it doesn't exist
echo "🔧 Setting up SonarQube project..."
curl -s -u "$SONAR_LOGIN:$SONAR_PASSWORD" \
    -X POST "$SONAR_HOST_URL/api/projects/create" \
    -d "project=$PROJECT_KEY&name=$PROJECT_NAME" > /dev/null 2>&1

# Run tests with coverage
echo ""
echo "🧪 Running tests with coverage..."
mvn clean test jacoco:report

if [ $? -ne 0 ]; then
    echo "❌ Tests failed"
    echo "📋 Please fix test failures before running SonarQube analysis"
    exit 1
fi

echo "✅ Tests completed successfully"

# Run SonarQube analysis
echo ""
echo "🔍 Running SonarQube analysis..."
mvn sonar:sonar \
    -Dsonar.projectKey="$PROJECT_KEY" \
    -Dsonar.projectName="$PROJECT_NAME" \
    -Dsonar.projectVersion="$PROJECT_VERSION" \
    -Dsonar.host.url="$SONAR_HOST_URL" \
    -Dsonar.login="$SONAR_LOGIN" \
    -Dsonar.password="$SONAR_PASSWORD" \
    -Dsonar.java.coveragePlugin=jacoco \
    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
    -Dsonar.junit.reportPaths=target/surefire-reports \
    -Dsonar.sources=src/main/java \
    -Dsonar.tests=src/test/java \
    -Dsonar.java.binaries=target/classes \
    -Dsonar.java.test.binaries=target/test-classes

if [ $? -ne 0 ]; then
    echo "❌ SonarQube analysis failed"
    echo "📋 Check Maven output for details"
    exit 1
fi

echo ""
echo "✅ SonarQube analysis completed successfully!"
echo ""

# Get analysis results
echo "📊 Analysis Results:"
echo "==================="
curl -s -u "$SONAR_LOGIN:$SONAR_PASSWORD" \
    "$SONAR_HOST_URL/api/measures/component?component=$PROJECT_KEY&metricKeys=alert_status,bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density,ncloc,sqale_rating,reliability_rating,security_rating"

echo ""
echo ""
echo "🌐 View detailed results at:"
echo "   $SONAR_HOST_URL/dashboard?id=$PROJECT_KEY"
echo ""

# Open SonarQube dashboard
echo "🌐 Opening SonarQube dashboard..."
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    open "$SONAR_HOST_URL/dashboard?id=$PROJECT_KEY"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    if command -v xdg-open &> /dev/null; then
        xdg-open "$SONAR_HOST_URL/dashboard?id=$PROJECT_KEY"
    else
        echo "📋 Open manually: $SONAR_HOST_URL/dashboard?id=$PROJECT_KEY"
    fi
fi

echo ""
echo "📊 Key Quality Gates:"
echo "   🐛 Bugs: Should be 0"
echo "   🔒 Vulnerabilities: Should be 0"
echo "   🧹 Code Smells: < 10"
echo "   📈 Coverage: > 80%"
echo "   📋 Duplications: < 3%"
echo "   ⭐ Rating: A or B"
echo ""

echo "Press any key to continue..."
read -n 1 -s
