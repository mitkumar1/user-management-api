@echo off
echo ========================================
echo    Angular Frontend Complete Setup
echo ========================================
echo.

echo Step 1: Setting up Node.js from zip file...
echo.

REM Check if running as administrator
net session >nul 2>&1
if %errorLevel% == 0 (
    echo Running as Administrator - Good!
) else (
    echo ERROR: This script must be run as Administrator
    echo Right-click Command Prompt and select "Run as administrator"
    pause
    exit /b 1
)

echo Step 2: Creating Node.js directory...
if not exist "C:\Program Files\nodejs" mkdir "C:\Program Files\nodejs"

echo Step 3: Copying Node.js files...
if exist "C:\Users\MI20046694\Downloads\node-v22.17.1-win-x64" (
    xcopy "C:\Users\MI20046694\Downloads\node-v22.17.1-win-x64\*" "C:\Program Files\nodejs\" /E /I /Y
    echo Node.js files copied successfully!
) else (
    echo ERROR: Node.js zip file not found at expected location
    echo Please ensure the zip file is extracted to:
    echo C:\Users\MI20046694\Downloads\node-v22.17.1-win-x64
    pause
    exit /b 1
)

echo Step 4: Adding Node.js to system PATH...
setx PATH "%PATH%;C:\Program Files\nodejs" /M

echo Step 5: Node.js setup complete!
echo Please close this window and open a NEW Command Prompt to continue...
echo.
echo After opening new Command Prompt, run: setup-angular.bat
echo.
pause
