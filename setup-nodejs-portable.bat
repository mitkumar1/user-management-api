@echo off
echo ========================================
echo    Portable Node.js Setup (No Admin)
echo ========================================
echo.

echo Step 1: Setting up Node.js in user directory...
set NODEJS_HOME=%USERPROFILE%\nodejs
set NODEJS_SOURCE=%USERPROFILE%\Downloads\node-v22.17.1-win-x64

echo Creating Node.js directory: %NODEJS_HOME%
if not exist "%NODEJS_HOME%" mkdir "%NODEJS_HOME%"

echo Step 2: Copying Node.js files...
if exist "%NODEJS_SOURCE%" (
    xcopy "%NODEJS_SOURCE%\*" "%NODEJS_HOME%\" /E /I /Y
    echo Node.js files copied successfully!
) else (
    echo ERROR: Node.js files not found at: %NODEJS_SOURCE%
    echo Please ensure the zip file is extracted to Downloads folder
    pause
    exit /b 1
)

echo Step 3: Setting up environment for current session...
set PATH=%NODEJS_HOME%;%PATH%

echo Step 4: Adding to user PATH permanently...
powershell -Command "[Environment]::SetEnvironmentVariable('PATH', [Environment]::GetEnvironmentVariable('PATH', 'User') + ';%NODEJS_HOME%', 'User')"

echo Step 5: Testing Node.js installation...
"%NODEJS_HOME%\node.exe" --version
"%NODEJS_HOME%\npm.cmd" --version

echo.
echo ========================================
echo    Node.js Setup Complete!
echo ========================================
echo.
echo Please close this window and open a NEW Command Prompt
echo Then run: setup-angular-portable.bat
echo.
pause
