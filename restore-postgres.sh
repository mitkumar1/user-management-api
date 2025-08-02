#!/bin/bash
# PostgreSQL Restore Script for User Management API
# Usage: ./restore-postgres.sh backup_file.sql.gz

# Configuration
DB_NAME="userdb"
DB_USER="dbuser"
DB_HOST="localhost"
DB_PORT="5432"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🔄 PostgreSQL Restore Script${NC}"

# Check if backup file is provided
if [ $# -eq 0 ]; then
    echo -e "${RED}❌ Usage: $0 <backup_file.sql.gz>${NC}"
    echo -e "${YELLOW}Example: $0 /var/backups/postgresql/userdb_backup_20250102_120000.sql.gz${NC}"
    exit 1
fi

BACKUP_FILE="$1"

# Check if backup file exists
if [ ! -f "$BACKUP_FILE" ]; then
    echo -e "${RED}❌ Backup file not found: $BACKUP_FILE${NC}"
    exit 1
fi

echo -e "${YELLOW}Backup file: $BACKUP_FILE${NC}"
echo -e "${YELLOW}Database: $DB_NAME${NC}"

# Check if PostgreSQL is running
if ! pg_isready -h $DB_HOST -p $DB_PORT -U $DB_USER >/dev/null 2>&1; then
    echo -e "${RED}❌ PostgreSQL is not running or not accessible${NC}"
    exit 1
fi

echo -e "${GREEN}✅ PostgreSQL is running${NC}"

# Get file extension
if [[ "$BACKUP_FILE" == *.gz ]]; then
    echo -e "${YELLOW}📦 Extracting compressed backup...${NC}"
    TEMP_FILE="/tmp/restore_temp.sql"
    
    if gunzip -c "$BACKUP_FILE" > "$TEMP_FILE"; then
        echo -e "${GREEN}✅ Backup extracted${NC}"
        RESTORE_FILE="$TEMP_FILE"
    else
        echo -e "${RED}❌ Failed to extract backup${NC}"
        exit 1
    fi
elif [[ "$BACKUP_FILE" == *.sql ]]; then
    RESTORE_FILE="$BACKUP_FILE"
else
    echo -e "${RED}❌ Unsupported file format. Use .sql or .sql.gz files${NC}"
    exit 1
fi

# Confirm restore operation
echo -e "${YELLOW}⚠️  WARNING: This will replace all data in database '$DB_NAME'${NC}"
read -p "Are you sure you want to continue? (y/N): " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo -e "${YELLOW}🚫 Restore cancelled${NC}"
    [ -f "$TEMP_FILE" ] && rm "$TEMP_FILE"
    exit 0
fi

# Create backup of current database before restore
echo -e "${YELLOW}💾 Creating backup of current database...${NC}"
CURRENT_BACKUP="/tmp/pre_restore_backup_$(date +%Y%m%d_%H%M%S).sql"
if pg_dump -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME > "$CURRENT_BACKUP"; then
    echo -e "${GREEN}✅ Current database backed up to: $CURRENT_BACKUP${NC}"
else
    echo -e "${RED}❌ Failed to backup current database${NC}"
    read -p "Continue with restore anyway? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo -e "${YELLOW}🚫 Restore cancelled${NC}"
        [ -f "$TEMP_FILE" ] && rm "$TEMP_FILE"
        exit 0
    fi
fi

# Drop and recreate database
echo -e "${YELLOW}🗑️  Dropping and recreating database...${NC}"
dropdb -h $DB_HOST -p $DB_PORT -U postgres $DB_NAME
if createdb -h $DB_HOST -p $DB_PORT -U postgres -O $DB_USER $DB_NAME; then
    echo -e "${GREEN}✅ Database recreated${NC}"
else
    echo -e "${RED}❌ Failed to recreate database${NC}"
    exit 1
fi

# Restore database
echo -e "${YELLOW}🔄 Restoring database...${NC}"
if psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME < "$RESTORE_FILE"; then
    echo -e "${GREEN}✅ Database restore completed successfully${NC}"
else
    echo -e "${RED}❌ Database restore failed${NC}"
    
    # Attempt to restore from pre-restore backup
    if [ -f "$CURRENT_BACKUP" ]; then
        echo -e "${YELLOW}🔄 Attempting to restore from pre-restore backup...${NC}"
        dropdb -h $DB_HOST -p $DB_PORT -U postgres $DB_NAME
        createdb -h $DB_HOST -p $DB_PORT -U postgres -O $DB_USER $DB_NAME
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME < "$CURRENT_BACKUP"
        echo -e "${GREEN}✅ Restored from pre-restore backup${NC}"
    fi
    
    [ -f "$TEMP_FILE" ] && rm "$TEMP_FILE"
    exit 1
fi

# Verify restore
echo -e "${YELLOW}🔍 Verifying restore...${NC}"
TABLE_COUNT=$(psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c "SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public';")
USER_COUNT=$(psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c "SELECT count(*) FROM users;" 2>/dev/null || echo "0")

echo -e "${GREEN}📊 Restore verification:${NC}"
echo -e "${GREEN}   Tables: $TABLE_COUNT${NC}"
echo -e "${GREEN}   Users: $USER_COUNT${NC}"

# Clean up
[ -f "$TEMP_FILE" ] && rm "$TEMP_FILE"

echo -e "${GREEN}🎉 Database restore completed successfully!${NC}"
echo -e "${YELLOW}💡 Don't forget to restart your application if it's running${NC}"
