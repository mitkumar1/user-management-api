@echo off
echo Setting up Angular 16 Frontend for User Management System
echo.

echo Step 1: Checking Node.js installation...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Node.js is not installed. Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)
echo Node.js is installed: 
node --version

echo.
echo Step 2: Checking npm installation...
npm --version
echo.

echo Step 3: Installing Angular CLI...
npm install -g @angular/cli@16
echo.

echo Step 4: Installing project dependencies...
cd frontend\user-management-ui
npm install
echo.

echo Step 5: Setup complete!
echo.
echo To start the development server:
echo   cd frontend\user-management-ui
echo   npm start
echo.
echo The application will be available at http://localhost:4200
echo Make sure your Spring Boot backend is running on http://localhost:8080
echo.
pause
