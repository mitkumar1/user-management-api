@echo off
REM Quick Docker Desktop Installation for Windows
REM This script helps install Docker Desktop quickly

echo Docker Desktop Installation Helper
echo ==================================

echo Checking if Docker is already installed...
docker --version >nul 2>&1
if %errorlevel% == 0 (
    echo Docker is already installed!
    docker --version
    echo.
    echo You can now run: start-monitoring-postgres.bat
    pause
    exit /b 0
)

echo Docker is not installed. Let's install it!
echo.

REM Check if we have Chocolatey
where choco >nul 2>&1
if %errorlevel% == 0 (
    echo Installing Docker Desktop via Chocolatey...
    choco install docker-desktop -y
    echo.
    echo Docker Desktop installation completed!
    echo Please restart your computer and then start Docker Desktop.
    pause
    exit /b 0
)

REM Manual installation instructions
echo Manual Installation Required:
echo ============================
echo.
echo 1. Download Docker Desktop for Windows:
echo    https://desktop.docker.com/win/main/amd64/Docker%%20Desktop%%20Installer.exe
echo.
echo 2. Or visit: https://www.docker.com/products/docker-desktop/
echo.
echo 3. Run the installer as Administrator
echo.
echo 4. Restart your computer when prompted
echo.
echo 5. Start Docker Desktop from the Start menu
echo.
echo 6. Wait for Docker to be ready (green whale icon in system tray)
echo.
echo 7. Then run: start-monitoring-postgres.bat
echo.

REM Try to open the download page
set /p OPEN_URL=Would you like to open the Docker Desktop download page? (y/N): 
if /i "%OPEN_URL%"=="y" (
    start https://www.docker.com/products/docker-desktop/
)

echo.
echo Alternative: Install via Windows Package Manager
echo ================================================
echo If you have winget installed, you can run:
echo    winget install Docker.DockerDesktop
echo.

pause
