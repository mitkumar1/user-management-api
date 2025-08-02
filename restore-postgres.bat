@echo off
REM PostgreSQL Restore Script for Windows
REM Usage: restore-postgres.bat backup_file.zip

setlocal enabledelayedexpansion

REM Configuration
set DB_NAME=userdb
set DB_USER=dbuser
set DB_HOST=localhost
set DB_PORT=5432

echo 🔄 PostgreSQL Restore Script

REM Check if backup file is provided
if "%1"=="" (
    echo ❌ Usage: %0 ^<backup_file.zip^>
    echo Example: %0 C:\backup\postgresql\userdb_backup_20250102_120000.sql.zip
    goto :end
)

set BACKUP_FILE=%1

REM Check if backup file exists
if not exist "%BACKUP_FILE%" (
    echo ❌ Backup file not found: %BACKUP_FILE%
    goto :end
)

echo Backup file: %BACKUP_FILE%
echo Database: %DB_NAME%

REM Check if PostgreSQL is accessible
pg_isready -h %DB_HOST% -p %DB_PORT% -U %DB_USER% >nul 2>&1
if errorlevel 1 (
    echo ❌ PostgreSQL is not running or not accessible
    goto :end
)

echo ✅ PostgreSQL is running

REM Extract backup file
echo 📦 Extracting backup file...
set TEMP_DIR=%TEMP%\postgres_restore_%RANDOM%
mkdir "%TEMP_DIR%"

REM Extract using PowerShell
powershell -command "Expand-Archive -Path '%BACKUP_FILE%' -DestinationPath '%TEMP_DIR%'"
if errorlevel 1 (
    echo ❌ Failed to extract backup file
    goto :cleanup
)

REM Find the SQL file
for %%f in ("%TEMP_DIR%\*.sql") do set RESTORE_FILE=%%f

if not exist "%RESTORE_FILE%" (
    echo ❌ No SQL file found in backup
    goto :cleanup
)

echo ✅ Backup extracted: %RESTORE_FILE%

REM Confirm restore operation
echo.
echo ⚠️  WARNING: This will replace all data in database '%DB_NAME%'
set /p CONFIRM=Are you sure you want to continue? (y/N): 
if /i not "%CONFIRM%"=="y" (
    echo 🚫 Restore cancelled
    goto :cleanup
)

REM Create backup of current database
echo 💾 Creating backup of current database...
set CURRENT_BACKUP=%TEMP%\pre_restore_backup_%DATE:~-4,4%%DATE:~-10,2%%DATE:~-7,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%.sql
set CURRENT_BACKUP=%CURRENT_BACKUP: =0%

pg_dump -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% > "%CURRENT_BACKUP%"
if errorlevel 1 (
    echo ❌ Failed to backup current database
    set /p CONTINUE=Continue with restore anyway? (y/N): 
    if /i not "!CONTINUE!"=="y" (
        echo 🚫 Restore cancelled
        goto :cleanup
    )
) else (
    echo ✅ Current database backed up to: %CURRENT_BACKUP%
)

REM Drop and recreate database
echo 🗑️  Dropping and recreating database...
dropdb -h %DB_HOST% -p %DB_PORT% -U postgres %DB_NAME%
createdb -h %DB_HOST% -p %DB_PORT% -U postgres -O %DB_USER% %DB_NAME%
if errorlevel 1 (
    echo ❌ Failed to recreate database
    goto :cleanup
)

echo ✅ Database recreated

REM Restore database
echo 🔄 Restoring database...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% < "%RESTORE_FILE%"
if errorlevel 1 (
    echo ❌ Database restore failed
    
    REM Attempt to restore from pre-restore backup
    if exist "%CURRENT_BACKUP%" (
        echo 🔄 Attempting to restore from pre-restore backup...
        dropdb -h %DB_HOST% -p %DB_PORT% -U postgres %DB_NAME%
        createdb -h %DB_HOST% -p %DB_PORT% -U postgres -O %DB_USER% %DB_NAME%
        psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% < "%CURRENT_BACKUP%"
        echo ✅ Restored from pre-restore backup
    )
    goto :cleanup
)

echo ✅ Database restore completed successfully

REM Verify restore
echo 🔍 Verifying restore...
for /f %%i in ('psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -c "SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public';"') do set TABLE_COUNT=%%i

REM Get user count (may fail if users table doesn't exist)
set USER_COUNT=0
for /f %%i in ('psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -c "SELECT count(*) FROM users;" 2^>nul') do set USER_COUNT=%%i

echo 📊 Restore verification:
echo    Tables: %TABLE_COUNT%
echo    Users: %USER_COUNT%

echo 🎉 Database restore completed successfully!
echo 💡 Don't forget to restart your application if it's running

:cleanup
REM Clean up temporary files
if exist "%TEMP_DIR%" rmdir /s /q "%TEMP_DIR%"

:end
echo.
echo 💡 Note: You may have been prompted for database passwords
echo    postgres password: SecureP@ssw0rd123
echo    dbuser password: SecureP@ssw0rd123
pause
