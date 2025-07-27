@echo off
echo Creating project archive for GitHub upload...

:: Create a temp directory
mkdir temp_upload 2>nul

:: Copy all necessary files
xcopy *.* temp_upload\ /E /H /Y
xcopy src temp_upload\src\ /E /H /Y
xcopy .vscode temp_upload\.vscode\ /E /H /Y 2>nul

:: Copy Docker files
copy Dockerfile temp_upload\ 2>nul
copy docker-compose.yml temp_upload\ 2>nul
copy .dockerignore temp_upload\ 2>nul

echo.
echo Files copied to temp_upload directory.
echo.
echo Next steps:
echo 1. Compress temp_upload folder to ZIP
echo 2. Upload to GitHub repository
echo 3. Open in Codespaces
echo.
pause
