@echo off
echo ========================================
echo    Angular Application Setup
echo ========================================
echo.

echo Step 1: Verifying Node.js installation...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Node.js not found in PATH
    echo Please run setup-nodejs.bat first as Administrator
    pause
    exit /b 1
)

echo Node.js version:
node --version
echo npm version:
npm --version
echo.

echo Step 2: Installing Angular CLI globally...
npm install -g @angular/cli@16
echo.

echo Step 3: Verifying Angular CLI installation...
ng version
echo.

echo Step 4: Navigating to Angular project...
cd frontend\user-management-ui
if %errorlevel% neq 0 (
    echo ERROR: Could not find Angular project directory
    echo Make sure you're running this from the project root
    pause
    exit /b 1
)

echo Step 5: Installing project dependencies...
echo This may take a few minutes...
npm install

echo Step 6: Starting Angular development server...
echo.
echo ========================================
echo    Setup Complete!
echo ========================================
echo.
echo The Angular application will start automatically.
echo Access it at: http://localhost:4200
echo.
echo Make sure your Spring Boot backend is running on port 8080
echo.
echo Press Ctrl+C to stop the development server
echo.

npm start
