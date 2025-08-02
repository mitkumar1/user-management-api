#!/bin/bash

# Load Testing Script for User Management API
# Tests authentication, user operations, and monitoring

BASE_URL="http://localhost:8080"
USERS=${1:-10}        # Default 10 users
DURATION=${2:-60}     # Default 60 seconds
DELAY=${3:-0.1}       # Default 0.1 seconds between requests

echo "🚀 Starting Load Test for User Management API"
echo "============================================="
echo "Target URL: $BASE_URL"
echo "Concurrent Users: $USERS"
echo "Duration: $DURATION seconds"
echo "Request Delay: $DELAY seconds"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
total_requests=0
successful_requests=0
failed_requests=0

# Create unique test user prefix
test_prefix="loadtest_$(date +%s)"

# Log file
log_file="load_test_$(date +%Y%m%d_%H%M%S).log"

echo "📝 Logging to: $log_file"
echo ""

# Function to log messages
log_message() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a "$log_file"
}

# Function to test health endpoint
test_health() {
    local user_id=$1
    response=$(curl -s -w "%{http_code}" -o /dev/null "$BASE_URL/actuator/health")
    total_requests=$((total_requests + 1))
    
    if [ "$response" = "200" ]; then
        successful_requests=$((successful_requests + 1))
        echo -n "."
    else
        failed_requests=$((failed_requests + 1))
        echo -n "E"
    fi
}

# Function to test user registration
test_registration() {
    local user_id=$1
    local username="${test_prefix}_user_${user_id}"
    
    response=$(curl -s -w "%{http_code}" -o /dev/null -X POST "$BASE_URL/api/auth/signup" \
        -H "Content-Type: application/json" \
        -d "{
            \"name\": \"Test User $user_id\",
            \"username\": \"$username\",
            \"email\": \"${username}@loadtest.com\",
            \"password\": \"Test123!@#\"
        }")
    
    total_requests=$((total_requests + 1))
    
    if [ "$response" = "200" ] || [ "$response" = "201" ]; then
        successful_requests=$((successful_requests + 1))
        echo -n "+"
    else
        failed_requests=$((failed_requests + 1))
        echo -n "R"
    fi
}

# Function to test user login
test_login() {
    local user_id=$1
    local username="${test_prefix}_user_${user_id}"
    
    token_response=$(curl -s -X POST "$BASE_URL/api/auth/signin" \
        -H "Content-Type: application/json" \
        -d "{
            \"usernameOrEmail\": \"$username\",
            \"password\": \"Test123!@#\"
        }")
    
    total_requests=$((total_requests + 1))
    
    # Check if login was successful and extract token
    if echo "$token_response" | grep -q "accessToken"; then
        successful_requests=$((successful_requests + 1))
        echo -n "L"
        
        # Extract token for future use
        token=$(echo "$token_response" | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)
        echo "$token" > "/tmp/${username}_token.txt"
    else
        failed_requests=$((failed_requests + 1))
        echo -n "l"
    fi
}

# Function to test authenticated endpoint
test_user_profile() {
    local user_id=$1
    local username="${test_prefix}_user_${user_id}"
    local token_file="/tmp/${username}_token.txt"
    
    if [ -f "$token_file" ]; then
        token=$(cat "$token_file")
        response=$(curl -s -w "%{http_code}" -o /dev/null -X GET "$BASE_URL/api/user/me" \
            -H "Authorization: Bearer $token")
        
        total_requests=$((total_requests + 1))
        
        if [ "$response" = "200" ]; then
            successful_requests=$((successful_requests + 1))
            echo -n "P"
        else
            failed_requests=$((failed_requests + 1))
            echo -n "p"
        fi
    fi
}

# Function to test metrics endpoint
test_metrics() {
    response=$(curl -s -w "%{http_code}" -o /dev/null "$BASE_URL/actuator/metrics")
    total_requests=$((total_requests + 1))
    
    if [ "$response" = "200" ]; then
        successful_requests=$((successful_requests + 1))
        echo -n "M"
    else
        failed_requests=$((failed_requests + 1))
        echo -n "m"
    fi
}

# Function to display progress
display_progress() {
    echo ""
    echo "Progress Legend:"
    echo ". = Health check success    E = Health check error"
    echo "+ = Registration success    R = Registration error"
    echo "L = Login success          l = Login error"
    echo "P = Profile success        p = Profile error"
    echo "M = Metrics success        m = Metrics error"
    echo ""
}

# Function to cleanup test users
cleanup_test_users() {
    echo ""
    log_message "🧹 Cleaning up test users..."
    rm -f /tmp/${test_prefix}_user_*_token.txt
}

# Function to display statistics
display_stats() {
    echo ""
    echo ""
    echo "📊 Load Test Results"
    echo "==================="
    echo "Total Requests: $total_requests"
    echo -e "Successful Requests: ${GREEN}$successful_requests${NC}"
    echo -e "Failed Requests: ${RED}$failed_requests${NC}"
    
    if [ $total_requests -gt 0 ]; then
        success_rate=$(( (successful_requests * 100) / total_requests ))
        echo -e "Success Rate: ${GREEN}$success_rate%${NC}"
        
        if [ $success_rate -ge 95 ]; then
            echo -e "Status: ${GREEN}✅ EXCELLENT${NC}"
        elif [ $success_rate -ge 90 ]; then
            echo -e "Status: ${YELLOW}⚠️  GOOD${NC}"
        else
            echo -e "Status: ${RED}❌ NEEDS ATTENTION${NC}"
        fi
    fi
    
    echo ""
    echo "📈 Check monitoring dashboards:"
    echo "- Grafana: http://localhost:3000"
    echo "- Prometheus: http://localhost:9090"
    echo "- Application Health: $BASE_URL/actuator/health"
    echo ""
}

# Trap to handle script interruption
trap 'echo ""; log_message "🛑 Load test interrupted"; cleanup_test_users; display_stats; exit 1' INT

# Check if API is accessible
log_message "🔍 Checking API accessibility..."
if ! curl -s --max-time 5 "$BASE_URL/actuator/health" > /dev/null; then
    echo -e "${RED}❌ API is not accessible at $BASE_URL${NC}"
    echo "Please ensure the application is running:"
    echo "  mvn spring-boot:run"
    echo "  or"
    echo "  docker-compose -f docker-compose.monitoring.yml up -d"
    exit 1
fi

echo -e "${GREEN}✅ API is accessible${NC}"
echo ""

display_progress

# Start load test
log_message "🚀 Starting load test..."
start_time=$(date +%s)
end_time=$((start_time + DURATION))

user_counter=1
request_counter=0

while [ $(date +%s) -lt $end_time ]; do
    # Test different endpoints in parallel
    for i in $(seq 1 $USERS); do
        current_user=$((user_counter + i))
        
        # Mix of different operations
        case $((request_counter % 6)) in
            0) test_health $current_user &;;
            1) test_registration $current_user &;;
            2) test_login $current_user &;;
            3) test_user_profile $current_user &;;
            4) test_metrics &;;
            5) test_health $current_user &;;
        esac
        
        request_counter=$((request_counter + 1))
        
        # Small delay to prevent overwhelming
        sleep $DELAY
    done
    
    user_counter=$((user_counter + USERS))
    
    # Wait for background processes to complete
    wait
    
    # Display current stats every 10 seconds
    current_time=$(date +%s)
    if [ $((current_time % 10)) -eq 0 ]; then
        elapsed=$((current_time - start_time))
        remaining=$((end_time - current_time))
        echo " [${elapsed}s elapsed, ${remaining}s remaining]"
    fi
done

# Wait for any remaining background processes
wait

log_message "✅ Load test completed"

# Cleanup and display results
cleanup_test_users
display_stats

# Save final stats to log
{
    echo ""
    echo "=== FINAL STATISTICS ==="
    echo "Total Requests: $total_requests"
    echo "Successful Requests: $successful_requests"
    echo "Failed Requests: $failed_requests"
    if [ $total_requests -gt 0 ]; then
        success_rate=$(( (successful_requests * 100) / total_requests ))
        echo "Success Rate: $success_rate%"
    fi
    echo "Duration: $DURATION seconds"
    echo "Users: $USERS"
    echo "========================"
} >> "$log_file"

echo "📁 Complete log saved to: $log_file"
