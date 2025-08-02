@echo off
REM Environment Variables Setup for PostgreSQL
REM Sets up secure environment variables for database connection

echo PostgreSQL Environment Variables Setup
echo ======================================

echo Setting up environment variables for secure database connection...
echo.

REM Set environment variables for current session
set DATABASE_URL=jdbc:postgresql://localhost:5432/userdb
set DATABASE_USERNAME=dbuser
set DATABASE_PASSWORD=SecureP@ssw0rd123

REM Set permanent environment variables for current user
setx DATABASE_URL "jdbc:postgresql://localhost:5432/userdb"
setx DATABASE_USERNAME "dbuser"
setx DATABASE_PASSWORD "SecureP@ssw0rd123"

echo Environment variables set:
echo - DATABASE_URL: %DATABASE_URL%
echo - DATABASE_USERNAME: %DATABASE_USERNAME%
echo - DATABASE_PASSWORD: [HIDDEN]

echo.
echo Security Note:
echo ==============
echo For production environments, please:
echo 1. Change the default password
echo 2. Use a secure password management system
echo 3. Set environment variables via system administrator
echo 4. Consider using Azure Key Vault or AWS Secrets Manager

echo.
echo Environment variables are now set!
echo You can start the application with: mvn spring-boot:run

pause
