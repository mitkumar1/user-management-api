#!/bin/bash
# JMeter Load Testing Script for User Management API (Linux/Mac)
# This script runs comprehensive load tests using Apache JMeter
# Usage: ./run-jmeter-test.sh [users] [ramp_time] [duration]

echo "===================================================="
echo "🚀 JMeter Load Testing - User Management API"
echo "===================================================="

# Set default values
USERS=${1:-10}
RAMP_TIME=${2:-30}
DURATION=${3:-120}

# Configuration
JMETER_HOME="/opt/apache-jmeter-5.6.2"
TEST_PLAN="jmeter-load-test.jmx"
TIMESTAMP=$(date +"%Y%m%d-%H%M%S")
RESULTS_FILE="jmeter-results-${TIMESTAMP}.jtl"
REPORT_DIR="jmeter-report-${TIMESTAMP}"
LOG_FILE="jmeter-test-${TIMESTAMP}.log"

echo "📊 Test Parameters:"
echo "   Users: $USERS"
echo "   Ramp Time: $RAMP_TIME seconds"
echo "   Duration: $DURATION seconds"
echo "   Results: $RESULTS_FILE"
echo "   Report: $REPORT_DIR"
echo ""

# Function to install JMeter
install_jmeter() {
    echo "📥 Installing JMeter..."
    
    # Check if running on macOS
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew &> /dev/null; then
            echo "Installing JMeter via Homebrew..."
            brew install jmeter
            JMETER_HOME="/usr/local/bin"
        else
            echo "❌ Homebrew not found. Please install Homebrew first:"
            echo "   /bin/bash -c \"\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)\""
            return 1
        fi
    else
        # Linux
        if ! command -v java &> /dev/null; then
            echo "Installing Java..."
            sudo apt-get update
            sudo apt-get install -y openjdk-11-jdk
        fi
        
        if [ ! -d "$JMETER_HOME" ]; then
            echo "Downloading JMeter 5.6.2..."
            cd /tmp
            wget https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.2.tgz
            
            echo "Extracting JMeter..."
            sudo tar -xzf apache-jmeter-5.6.2.tgz -C /opt/
            sudo chown -R $USER:$USER /opt/apache-jmeter-5.6.2
            
            # Add to PATH
            echo 'export PATH=$PATH:/opt/apache-jmeter-5.6.2/bin' >> ~/.bashrc
            source ~/.bashrc
            
            rm apache-jmeter-5.6.2.tgz
            cd - > /dev/null
        fi
    fi
    
    echo "✅ JMeter installation completed"
    return 0
}

# Check if JMeter is installed
if ! command -v jmeter &> /dev/null && [ ! -f "$JMETER_HOME/bin/jmeter" ]; then
    echo "❌ JMeter not found"
    echo "📥 Installing JMeter..."
    if ! install_jmeter; then
        echo "❌ Failed to install JMeter"
        exit 1
    fi
fi

# Determine JMeter command
if command -v jmeter &> /dev/null; then
    JMETER_CMD="jmeter"
else
    JMETER_CMD="$JMETER_HOME/bin/jmeter"
fi

# Check if API is running
echo "🔍 Checking if API is running..."
if ! curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "❌ API not accessible at http://localhost:8080"
    echo "🚀 Please start the application first:"
    echo "   mvn spring-boot:run"
    exit 1
fi

echo "✅ API is running and accessible"

# Create results directory
mkdir -p results

# Run JMeter test
echo ""
echo "🧪 Starting JMeter Load Test..."
echo "⏰ Test will run for $DURATION seconds with $USERS users"
echo ""

$JMETER_CMD \
    -n \
    -t "$TEST_PLAN" \
    -l "results/$RESULTS_FILE" \
    -e \
    -o "results/$REPORT_DIR" \
    -JUSERS=$USERS \
    -JRAMP_TIME=$RAMP_TIME \
    -JDURATION=$DURATION \
    -JBASE_URL=http://localhost:8080 \
    -j "results/$LOG_FILE"

if [ $? -ne 0 ]; then
    echo "❌ JMeter test failed"
    echo "📋 Check log file: results/$LOG_FILE"
    exit 1
fi

echo ""
echo "✅ JMeter test completed successfully!"
echo ""
echo "📊 Results:"
echo "   📄 Raw Results: results/$RESULTS_FILE"
echo "   📈 HTML Report: results/$REPORT_DIR/index.html"
echo "   📋 Log File: results/$LOG_FILE"
echo ""

# Display summary
echo "📋 Test Summary:"
echo "================"
grep "summary" "results/$LOG_FILE" || echo "Summary not found in log file"

echo ""
echo "🌐 Opening HTML report..."
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    open "results/$REPORT_DIR/index.html"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    if command -v xdg-open &> /dev/null; then
        xdg-open "results/$REPORT_DIR/index.html"
    else
        echo "📋 Open manually: results/$REPORT_DIR/index.html"
    fi
fi

echo ""
echo "📊 Monitor real-time metrics at:"
echo "   Grafana: http://localhost:3000"
echo "   Prometheus: http://localhost:9090"
echo "   Application: http://localhost:8080/actuator/metrics"
echo ""

echo "Press any key to continue..."
read -n 1 -s
