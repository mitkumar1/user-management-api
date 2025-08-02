# PostgreSQL Production Database Setup

## Overview
This guide covers setting up PostgreSQL as the production database for the User Management API, including installation, configuration, and integration.

## 1. PostgreSQL Installation

### Option A: Docker Installation (Recommended for Development)

```bash
# Pull PostgreSQL Docker image
docker pull postgres:15-alpine

# Run PostgreSQL container
docker run -d \
  --name postgres-prod \
  -e POSTGRES_DB=userdb \
  -e POSTGRES_USER=dbuser \
  -e POSTGRES_PASSWORD=SecureP@ssw0rd123 \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  postgres:15-alpine

# Check if container is running
docker ps
```

### Option B: Windows Installation

1. **Download PostgreSQL**:
   - Go to https://www.postgresql.org/download/windows/
   - Download PostgreSQL 15 installer
   - Run the installer as Administrator

2. **Installation Steps**:
   - Choose installation directory: `C:\Program Files\PostgreSQL\15`
   - Select components: PostgreSQL Server, pgAdmin 4, Command Line Tools
   - Set data directory: `C:\Program Files\PostgreSQL\15\data`
   - Set superuser password: `SecureP@ssw0rd123`
   - Port: `5432`
   - Locale: Default

3. **Add to PATH**:
   ```cmd
   setx PATH "%PATH%;C:\Program Files\PostgreSQL\15\bin"
   ```

### Option C: Linux Installation (Ubuntu/Debian)

```bash
# Update package list
sudo apt update

# Install PostgreSQL
sudo apt install postgresql postgresql-contrib

# Start and enable PostgreSQL
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Set password for postgres user
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'SecureP@ssw0rd123';"
```

## 2. Database Setup

### Create Application Database and User

```sql
-- Connect to PostgreSQL as superuser
psql -U postgres -h localhost

-- Create database
CREATE DATABASE userdb;

-- Create application user
CREATE USER dbuser WITH ENCRYPTED PASSWORD 'SecureP@ssw0rd123';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE userdb TO dbuser;

-- Grant schema privileges
\c userdb;
GRANT ALL ON SCHEMA public TO dbuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO dbuser;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO dbuser;

-- Exit
\q
```

### Database Initialization Script

Create `database/init.sql`:

```sql
-- User Management Database Schema
-- ================================

-- Create sequences
CREATE SEQUENCE IF NOT EXISTS user_id_seq START 1;
CREATE SEQUENCE IF NOT EXISTS role_id_seq START 1;

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY DEFAULT nextval('role_id_seq'),
    name VARCHAR(60) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN DEFAULT true,
    email_verified BOOLEAN DEFAULT false
);

-- Create user_roles junction table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_active ON users(is_active);
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at);
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role_id ON user_roles(role_id);

-- Insert default roles
INSERT INTO roles (name) VALUES ('ROLE_USER') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR') ON CONFLICT (name) DO NOTHING;

-- Create updated_at trigger function
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply trigger to tables
DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_roles_updated_at ON roles;
CREATE TRIGGER update_roles_updated_at BEFORE UPDATE ON roles 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Create audit log table
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    operation VARCHAR(10) NOT NULL,
    user_id BIGINT,
    old_data JSONB,
    new_data JSONB,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_table_name ON audit_logs(table_name);
CREATE INDEX IF NOT EXISTS idx_audit_logs_timestamp ON audit_logs(timestamp);
```

## 3. Spring Boot Configuration

### Update `application.properties`

```properties
# PostgreSQL Production Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=dbuser
spring.datasource.password=SecureP@ssw0rd123
spring.datasource.driver-class-name=org.postgresql.Driver

# Connection Pool Configuration
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=600000
spring.datasource.hikari.connection-timeout=30000

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true

# Performance Settings
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.use_query_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.jcache.JCacheRegionFactory

# Database Migration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

### Environment-Specific Configuration

Create `application-production.properties`:

```properties
# Production Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/userdb}
spring.datasource.username=${DATABASE_USERNAME:dbuser}
spring.datasource.password=${DATABASE_PASSWORD:SecureP@ssw0rd123}

# Production JPA Settings
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Connection Pool for Production
spring.datasource.hikari.maximum-pool-size=50
spring.datasource.hikari.minimum-idle=10

# Logging
logging.level.org.hibernate.SQL=WARN
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=WARN
```

## 4. Docker Compose Integration

Update `docker-compose.monitoring.yml`:

```yaml
services:
  # PostgreSQL Production Database
  postgres:
    image: postgres:15-alpine
    container_name: postgres-prod
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: dbuser
      POSTGRES_PASSWORD: SecureP@ssw0rd123
      PGDATA: /var/lib/postgresql/data/pgdata
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./database/init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - monitoring
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U dbuser -d userdb"]
      interval: 30s
      timeout: 10s
      retries: 5
      start_period: 30s

  # pgAdmin for database management
  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@example.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "8082:80"
    volumes:
      - pgadmin_data:/var/lib/pgadmin
    networks:
      - monitoring
    depends_on:
      - postgres

volumes:
  postgres_data:
  pgadmin_data:
```

## 5. Database Migration with Flyway

### Add Flyway dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

### Create migration files in `src/main/resources/db/migration/`:

**V1__Initial_schema.sql:**
```sql
-- Initial database schema
CREATE SEQUENCE user_id_seq START 1;
CREATE SEQUENCE role_id_seq START 1;

CREATE TABLE roles (
    id BIGINT PRIMARY KEY DEFAULT nextval('role_id_seq'),
    name VARCHAR(60) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
```

## 6. Monitoring and Performance

### Database Monitoring Queries

```sql
-- Check active connections
SELECT count(*) as active_connections 
FROM pg_stat_activity 
WHERE state = 'active';

-- Check database size
SELECT pg_size_pretty(pg_database_size('userdb')) as database_size;

-- Check table sizes
SELECT schemaname, tablename, 
       pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) as size
FROM pg_tables 
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;

-- Check slow queries
SELECT query, mean_time, calls, total_time
FROM pg_stat_statements
ORDER BY mean_time DESC
LIMIT 10;
```

### Performance Tuning

Add to `postgresql.conf`:

```conf
# Memory
shared_buffers = 256MB
effective_cache_size = 1GB
work_mem = 4MB
maintenance_work_mem = 64MB

# WAL
wal_buffers = 16MB
checkpoint_completion_target = 0.9
checkpoint_timeout = 10min

# Connection
max_connections = 100
```

## 7. Backup and Recovery

### Automated Backup Script

```bash
#!/bin/bash
# backup.sh

BACKUP_DIR="/var/backups/postgresql"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
DB_NAME="userdb"
DB_USER="dbuser"

# Create backup directory
mkdir -p $BACKUP_DIR

# Create backup
pg_dump -U $DB_USER -h localhost -d $DB_NAME > $BACKUP_DIR/userdb_backup_$TIMESTAMP.sql

# Compress backup
gzip $BACKUP_DIR/userdb_backup_$TIMESTAMP.sql

# Remove backups older than 7 days
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "Backup completed: userdb_backup_$TIMESTAMP.sql.gz"
```

### Restore from Backup

```bash
# Restore database
psql -U dbuser -h localhost -d userdb < backup_file.sql
```

## 8. Security Configuration

### SSL Configuration

```properties
# SSL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb?sslmode=require
```

### Connection Security

```sql
-- Create read-only user for reporting
CREATE USER readonly_user WITH PASSWORD 'ReadOnlyP@ss123';
GRANT CONNECT ON DATABASE userdb TO readonly_user;
GRANT USAGE ON SCHEMA public TO readonly_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO readonly_user;
```

## 9. Quick Start Commands

```bash
# Start PostgreSQL with Docker
docker-compose -f docker-compose.monitoring.yml up -d postgres pgadmin

# Connect to database
psql -U dbuser -h localhost -d userdb

# Run application with PostgreSQL
mvn spring-boot:run -Dspring.profiles.active=production

# Check database status
docker exec postgres-prod pg_isready -U dbuser -d userdb

# View logs
docker logs postgres-prod
```

## 10. Troubleshooting

### Common Issues

1. **Connection refused**: Check if PostgreSQL is running
2. **Authentication failed**: Verify username/password
3. **Database does not exist**: Create database first
4. **Permission denied**: Check user privileges

### Health Checks

```bash
# Test connection
pg_isready -h localhost -p 5432 -U dbuser

# Check database size
psql -U dbuser -h localhost -d userdb -c "SELECT pg_size_pretty(pg_database_size('userdb'));"
```

## Access URLs

- **pgAdmin**: http://localhost:8082 (admin@example.com/admin123)
- **Database**: localhost:5432
- **Application Health**: http://localhost:8080/actuator/health

Your PostgreSQL production database is now ready! 🚀
