#!/bin/bash
# PostgreSQL Backup Script for User Management API
# Usage: ./backup-postgres.sh

# Configuration
BACKUP_DIR="/var/backups/postgresql"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
DB_NAME="userdb"
DB_USER="dbuser"
DB_HOST="localhost"
DB_PORT="5432"
RETENTION_DAYS=7

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🗄️  PostgreSQL Backup Script${NC}"
echo -e "${YELLOW}Database: $DB_NAME${NC}"
echo -e "${YELLOW}Timestamp: $TIMESTAMP${NC}"

# Create backup directory if it doesn't exist
mkdir -p $BACKUP_DIR

# Check if PostgreSQL is running
if ! pg_isready -h $DB_HOST -p $DB_PORT -U $DB_USER >/dev/null 2>&1; then
    echo -e "${RED}❌ PostgreSQL is not running or not accessible${NC}"
    exit 1
fi

echo -e "${GREEN}✅ PostgreSQL is running${NC}"

# Create backup
echo -e "${YELLOW}📦 Creating backup...${NC}"
BACKUP_FILE="$BACKUP_DIR/userdb_backup_$TIMESTAMP.sql"

if pg_dump -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME > $BACKUP_FILE; then
    echo -e "${GREEN}✅ Database backup created: $BACKUP_FILE${NC}"
    
    # Compress backup
    echo -e "${YELLOW}🗜️  Compressing backup...${NC}"
    gzip $BACKUP_FILE
    COMPRESSED_FILE="$BACKUP_FILE.gz"
    
    if [ -f "$COMPRESSED_FILE" ]; then
        echo -e "${GREEN}✅ Backup compressed: $COMPRESSED_FILE${NC}"
        
        # Get file size
        FILE_SIZE=$(du -h "$COMPRESSED_FILE" | cut -f1)
        echo -e "${GREEN}📊 Backup size: $FILE_SIZE${NC}"
    else
        echo -e "${RED}❌ Failed to compress backup${NC}"
    fi
else
    echo -e "${RED}❌ Failed to create database backup${NC}"
    exit 1
fi

# Remove old backups
echo -e "${YELLOW}🧹 Cleaning up old backups (older than $RETENTION_DAYS days)...${NC}"
DELETED_COUNT=$(find $BACKUP_DIR -name "userdb_backup_*.sql.gz" -mtime +$RETENTION_DAYS -delete -print | wc -l)

if [ $DELETED_COUNT -gt 0 ]; then
    echo -e "${GREEN}✅ Removed $DELETED_COUNT old backup(s)${NC}"
else
    echo -e "${YELLOW}ℹ️  No old backups to remove${NC}"
fi

# List current backups
echo -e "${YELLOW}📋 Current backups:${NC}"
ls -lh $BACKUP_DIR/userdb_backup_*.sql.gz 2>/dev/null | awk '{print $9, $5, $6, $7, $8}' | column -t

echo -e "${GREEN}🎉 Backup completed successfully!${NC}"

# Optional: Upload to cloud storage
# Uncomment and configure for your cloud storage provider
# aws s3 cp $COMPRESSED_FILE s3://your-backup-bucket/postgresql/
# gsutil cp $COMPRESSED_FILE gs://your-backup-bucket/postgresql/
