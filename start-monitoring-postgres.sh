#!/bin/bash
# Quick Start Script with PostgreSQL
# Sets up PostgreSQL and starts the complete monitoring stack

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}🚀 User Management API - Quick Start with PostgreSQL${NC}"
echo -e "${BLUE}Setting up PostgreSQL and monitoring stack...${NC}"

# Check if Docker is available
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker is not installed. Please install Docker first.${NC}"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}❌ Docker Compose is not installed. Please install Docker Compose first.${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Docker and Docker Compose are available${NC}"

# Check if database directory exists
if [ ! -f "database/init.sql" ]; then
    echo -e "${YELLOW}⚠️  Database initialization script not found${NC}"
    echo -e "${YELLOW}Creating basic database structure...${NC}"
    
    mkdir -p database
    cat > database/init.sql << 'EOF'
-- Basic User Management Database Schema
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
EOF
    echo -e "${GREEN}✅ Database initialization script created${NC}"
fi

# Start PostgreSQL and pgAdmin first
echo -e "${YELLOW}🐘 Starting PostgreSQL database...${NC}"
docker-compose -f docker-compose.monitoring.yml up -d postgres pgadmin

# Wait for PostgreSQL to be ready
echo -e "${YELLOW}⏳ Waiting for PostgreSQL to be ready...${NC}"
for i in {1..30}; do
    if docker exec postgres-prod pg_isready -U dbuser -d userdb >/dev/null 2>&1; then
        echo -e "${GREEN}✅ PostgreSQL is ready${NC}"
        break
    fi
    echo -e "${YELLOW}   Waiting... ($i/30)${NC}"
    sleep 2
done

# Check if PostgreSQL is ready
if ! docker exec postgres-prod pg_isready -U dbuser -d userdb >/dev/null 2>&1; then
    echo -e "${RED}❌ PostgreSQL failed to start. Check logs: docker logs postgres-prod${NC}"
    exit 1
fi

# Build the application if needed
if [ ! -f "target/user-management-api-1.0.0-SNAPSHOT.jar" ]; then
    echo -e "${YELLOW}🔨 Building application...${NC}"
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo -e "${RED}❌ Application build failed${NC}"
        exit 1
    fi
    echo -e "${GREEN}✅ Application built successfully${NC}"
fi

# Start the complete monitoring stack
echo -e "${YELLOW}📊 Starting complete monitoring stack...${NC}"
docker-compose -f docker-compose.monitoring.yml up -d

# Wait for services to be ready
echo -e "${YELLOW}⏳ Waiting for services to start...${NC}"
sleep 30

# Check service status
echo -e "${BLUE}🔍 Checking service status...${NC}"

services=("postgres-prod:5432" "pgadmin:80" "prometheus:9090" "grafana:3000" "sonarqube:9000")
service_names=("PostgreSQL" "pgAdmin" "Prometheus" "Grafana" "SonarQube")

for i in "${!services[@]}"; do
    service="${services[$i]}"
    name="${service_names[$i]}"
    
    if docker exec user-management-api nc -z ${service/:/ } 2>/dev/null; then
        echo -e "${GREEN}✅ $name is running${NC}"
    else
        echo -e "${YELLOW}⏳ $name is starting...${NC}"
    fi
done

# Display access information
echo -e "${GREEN}"
echo "🎉 Setup completed! Access your services:"
echo "┌─────────────────────────────────────────────────────────┐"
echo "│  Service     │  URL                    │  Credentials  │"
echo "├─────────────────────────────────────────────────────────┤"
echo "│  Application │  http://localhost:8080  │  N/A          │"
echo "│  PostgreSQL  │  localhost:5432         │  dbuser/***   │"
echo "│  pgAdmin     │  http://localhost:8082  │  admin/admin123 │"
echo "│  Grafana     │  http://localhost:3000  │  admin/admin  │"
echo "│  Prometheus  │  http://localhost:9090  │  N/A          │"
echo "│  SonarQube   │  http://localhost:9000  │  admin/admin  │"
echo "└─────────────────────────────────────────────────────────┘"
echo -e "${NC}"

echo -e "${BLUE}📋 Useful commands:${NC}"
echo -e "${YELLOW}View logs:${NC} docker-compose -f docker-compose.monitoring.yml logs -f"
echo -e "${YELLOW}Stop services:${NC} docker-compose -f docker-compose.monitoring.yml down"
echo -e "${YELLOW}Database backup:${NC} ./backup-postgres.sh"
echo -e "${YELLOW}Check health:${NC} curl http://localhost:8080/actuator/health"

echo -e "${GREEN}🔍 Next steps:${NC}"
echo "1. 📊 Open Grafana and import dashboards"
echo "2. 🔍 Run SonarQube analysis: mvn sonar:sonar"
echo "3. 🧪 Test the API endpoints"
echo "4. 📈 Monitor application metrics"

echo -e "${GREEN}🚀 User Management API with PostgreSQL is ready!${NC}"
