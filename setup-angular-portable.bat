@echo off
echo ========================================
echo    Angular Setup (Portable)
echo ========================================
echo.

set NODEJS_HOME=%USERPROFILE%\nodejs

echo Step 1: Verifying Node.js installation...
if not exist "%NODEJS_HOME%\node.exe" (
    echo ERROR: Node.js not found at %NODEJS_HOME%
    echo Please run setup-nodejs-portable.bat first
    pause
    exit /b 1
)

echo Node.js found at: %NODEJS_HOME%
echo Setting PATH for this session...
set PATH=%NODEJS_HOME%;%PATH%

echo Node.js version:
node --version
echo npm version:
npm --version
echo.

echo Step 2: Installing Angular CLI globally (in user directory)...
npm install -g @angular/cli@16
echo.

echo Step 3: Verifying Angular CLI...
ng version
echo.

echo Step 4: Navigating to Angular project...
cd frontend\user-management-ui
if %errorlevel% neq 0 (
    echo ERROR: Could not find Angular project directory
    echo Current directory: %CD%
    echo Please ensure you're in the project root directory
    pause
    exit /b 1
)

echo Step 5: Installing project dependencies...
echo This may take several minutes...
npm install

if %errorlevel% neq 0 (
    echo ERROR: Failed to install dependencies
    echo This might be due to corporate firewall or proxy settings
    echo.
    echo Try these solutions:
    echo 1. Configure npm proxy: npm config set proxy http://your-proxy:port
    echo 2. Configure npm registry: npm config set registry https://registry.npmjs.org/
    echo 3. Contact IT support for npm/Node.js permissions
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Setup Complete!
echo ========================================
echo.
echo Starting Angular development server...
echo Access at: http://localhost:4200
echo.
echo Make sure Spring Boot backend is running on port 8080
echo Press Ctrl+C to stop the server
echo.

npm start
