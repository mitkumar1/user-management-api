@echo off
REM PostgreSQL Backup Script for Windows
REM Usage: backup-postgres.bat

setlocal enabledelayedexpansion

REM Configuration
set BACKUP_DIR=C:\backup\postgresql
set TIMESTAMP=%DATE:~-4,4%%DATE:~-10,2%%DATE:~-7,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
set TIMESTAMP=%TIMESTAMP: =0%
set DB_NAME=userdb
set DB_USER=dbuser
set DB_HOST=localhost
set DB_PORT=5432
set RETENTION_DAYS=7

echo 🗄️  PostgreSQL Backup Script
echo Database: %DB_NAME%
echo Timestamp: %TIMESTAMP%

REM Create backup directory if it doesn't exist
if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"

REM Check if PostgreSQL is accessible
pg_isready -h %DB_HOST% -p %DB_PORT% -U %DB_USER% >nul 2>&1
if errorlevel 1 (
    echo ❌ PostgreSQL is not running or not accessible
    exit /b 1
)

echo ✅ PostgreSQL is running

REM Create backup
echo 📦 Creating backup...
set BACKUP_FILE=%BACKUP_DIR%\userdb_backup_%TIMESTAMP%.sql

pg_dump -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% > "%BACKUP_FILE%"
if errorlevel 1 (
    echo ❌ Failed to create database backup
    exit /b 1
)

echo ✅ Database backup created: %BACKUP_FILE%

REM Compress backup using PowerShell
echo 🗜️  Compressing backup...
powershell -command "Compress-Archive -Path '%BACKUP_FILE%' -DestinationPath '%BACKUP_FILE%.zip'"
if exist "%BACKUP_FILE%.zip" (
    echo ✅ Backup compressed: %BACKUP_FILE%.zip
    del "%BACKUP_FILE%"
) else (
    echo ❌ Failed to compress backup
)

REM Remove old backups (Windows doesn't have find with -mtime, so we use PowerShell)
echo 🧹 Cleaning up old backups...
powershell -command "Get-ChildItem '%BACKUP_DIR%' -Filter 'userdb_backup_*.zip' | Where-Object {$_.LastWriteTime -lt (Get-Date).AddDays(-%RETENTION_DAYS%)} | Remove-Item -Force"

REM List current backups
echo 📋 Current backups:
dir "%BACKUP_DIR%\userdb_backup_*.zip" /O-D 2>nul

echo 🎉 Backup completed successfully!

REM Set password prompt message
echo.
echo 💡 Note: You may be prompted for the database password
echo    Password: SecureP@ssw0rd123

endlocal
