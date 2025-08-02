#!/bin/bash

echo "🚀 Starting User Management API Monitoring Stack"
echo "=================================================="

# Create necessary directories
echo "📁 Creating monitoring directories..."
mkdir -p monitoring/grafana/dashboards
mkdir -p monitoring/appd

# Start the monitoring stack
echo "🐳 Starting Docker containers..."
docker-compose -f docker-compose.monitoring.yml up -d

# Wait for services to be ready
echo "⏳ Waiting for services to start..."
sleep 30

# Check service status
echo "🔍 Checking service status..."
echo "Prometheus: http://localhost:9090"
docker ps --filter "name=prometheus" --format "table {{.Names}}\t{{.Status}}"

echo "Grafana: http://localhost:3000 (admin/admin)"
docker ps --filter "name=grafana" --format "table {{.Names}}\t{{.Status}}"

echo "SonarQube: http://localhost:9000 (admin/admin)"
docker ps --filter "name=sonarqube" --format "table {{.Names}}\t{{.Status}}"

echo "PostgreSQL: localhost:5432"
docker ps --filter "name=postgres" --format "table {{.Names}}\t{{.Status}}"

echo ""
echo "📊 Monitoring Stack URLs:"
echo "========================="
echo "🎯 Application:     http://localhost:8080"
echo "📈 Prometheus:      http://localhost:9090"
echo "📊 Grafana:         http://localhost:3000 (admin/admin)"
echo "🔍 SonarQube:       http://localhost:9000 (admin/admin)"
echo "📡 Node Exporter:   http://localhost:9100"
echo "🐳 cAdvisor:        http://localhost:8081"
echo "🏥 Health Check:    http://localhost:8080/actuator/health"
echo "📊 Metrics:         http://localhost:8080/actuator/prometheus"

echo ""
echo "🔧 Quick Commands:"
echo "=================="
echo "Run SonarQube analysis:"
echo "  mvn clean verify sonar:sonar"
echo ""
echo "Start application with monitoring:"
echo "  mvn spring-boot:run -Dspring.profiles.active=monitoring"
echo ""
echo "Stop monitoring stack:"
echo "  docker-compose -f docker-compose.monitoring.yml down"

echo ""
echo "✅ Monitoring stack is ready!"
