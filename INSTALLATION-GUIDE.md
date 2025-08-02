# Installation Prerequisites

## Overview
This guide helps you install the required software to run the User Management API with PostgreSQL and monitoring stack.

## 1. Docker Installation

### Windows Installation

1. **Download Docker Desktop**:
   - Go to https://www.docker.com/products/docker-desktop/
   - Download Docker Desktop for Windows
   - File size: ~500MB

2. **Install Docker Desktop**:
   - Run the installer as Administrator
   - Accept the license agreement
   - Enable WSL 2 integration if prompted
   - Restart your computer when prompted

3. **Verify Installation**:
   ```cmd
   docker --version
   docker compose version
   ```

4. **Start Docker Desktop**:
   - Search for "Docker Desktop" in Start menu
   - Start the application
   - Wait for Docker engine to start (green status)

### Alternative: Docker without Docker Desktop

If you prefer not to use Docker Desktop:

1. **Install Docker Engine**:
   ```powershell
   # Using Chocolatey
   choco install docker-engine
   
   # Or using winget
   winget install Docker.DockerCLI
   ```

2. **Install Docker Compose**:
   ```powershell
   # Download Docker Compose
   curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-windows-x86_64.exe" -o docker-compose.exe
   
   # Move to a directory in PATH
   move docker-compose.exe C:\Windows\System32\
   ```

## 2. PostgreSQL Installation Options

### Option A: PostgreSQL with Docker (Recommended)

No additional installation needed - included in our Docker setup!

```bash
# Start PostgreSQL with our monitoring stack
start-monitoring-postgres.bat
```

### Option B: Native PostgreSQL Installation

1. **Download PostgreSQL**:
   - Go to https://www.postgresql.org/download/windows/
   - Download PostgreSQL 15 installer
   - File size: ~350MB

2. **Installation Settings**:
   - Installation Directory: `C:\Program Files\PostgreSQL\15`
   - Data Directory: `C:\Program Files\PostgreSQL\15\data`
   - Port: `5432`
   - Superuser password: `SecureP@ssw0rd123`
   - Locale: Default

3. **Add to PATH**:
   ```cmd
   setx PATH "%PATH%;C:\Program Files\PostgreSQL\15\bin"
   ```

4. **Verify Installation**:
   ```cmd
   psql --version
   pg_isready
   ```

5. **Run Database Setup**:
   ```cmd
   setup-postgres.bat
   ```

## 3. Java Development Kit (JDK)

### Check if Java is already installed

```cmd
java -version
javac -version
```

### Install JDK 17 (Required)

1. **Download Options**:
   - **Oracle JDK 17**: https://www.oracle.com/java/technologies/downloads/#java17
   - **OpenJDK 17**: https://adoptium.net/temurin/releases/?version=17
   - **Microsoft OpenJDK**: https://docs.microsoft.com/en-us/java/openjdk/download

2. **Using Package Managers**:
   ```powershell
   # Using Chocolatey
   choco install openjdk17
   
   # Using winget
   winget install Microsoft.OpenJDK.17
   
   # Using Scoop
   scoop install openjdk17
   ```

3. **Verify Installation**:
   ```cmd
   java -version
   # Should show version 17.x.x
   ```

## 4. Maven Build Tool

### Check if Maven is installed

```cmd
mvn -version
```

### Install Maven

1. **Download Maven**:
   - Go to https://maven.apache.org/download.cgi
   - Download Binary zip archive
   - Extract to `C:\Program Files\Apache\maven`

2. **Set Environment Variables**:
   ```cmd
   setx JAVA_HOME "C:\Program Files\Java\jdk-17"
   setx M2_HOME "C:\Program Files\Apache\maven"
   setx PATH "%PATH%;%M2_HOME%\bin"
   ```

3. **Using Package Managers**:
   ```powershell
   # Using Chocolatey
   choco install maven
   
   # Using Scoop
   scoop install maven
   ```

4. **Verify Installation**:
   ```cmd
   mvn -version
   ```

## 5. Git Version Control

### Check if Git is installed

```cmd
git --version
```

### Install Git

1. **Download Git**:
   - Go to https://git-scm.com/downloads
   - Download Git for Windows
   - Run installer with default settings

2. **Using Package Managers**:
   ```powershell
   # Using Chocolatey
   choco install git
   
   # Using winget
   winget install Git.Git
   ```

3. **Configure Git**:
   ```cmd
   git config --global user.name "Your Name"
   git config --global user.email "your.email@example.com"
   ```

## 6. Quick Installation Script

Create and run this PowerShell script as Administrator:

```powershell
# install-prerequisites.ps1

# Check if running as Administrator
if (-NOT ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) {
    Write-Host "Please run as Administrator" -ForegroundColor Red
    exit 1
}

# Install Chocolatey if not present
if (!(Get-Command choco -ErrorAction SilentlyContinue)) {
    Write-Host "Installing Chocolatey..." -ForegroundColor Yellow
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
}

# Install required software
Write-Host "Installing prerequisites..." -ForegroundColor Green
choco install openjdk17 maven git docker-desktop -y

Write-Host "Installation completed!" -ForegroundColor Green
Write-Host "Please restart your computer and then run: start-monitoring-postgres.bat" -ForegroundColor Yellow
```

## 7. Verification Checklist

Run these commands to verify everything is installed correctly:

```cmd
# Check versions
docker --version
docker compose version
java -version
mvn -version
git --version
psql --version

# Test Docker
docker run hello-world

# Test PostgreSQL connection (after running setup-postgres.bat)
pg_isready -h localhost -p 5432 -U dbuser
```

## 8. Troubleshooting

### Docker Issues

**Issue**: "Docker daemon is not running"
**Solution**: Start Docker Desktop or Docker service

**Issue**: "docker: command not found"
**Solution**: Add Docker to PATH or restart terminal

### PostgreSQL Issues

**Issue**: "psql: command not found"
**Solution**: Add PostgreSQL bin directory to PATH

**Issue**: "Connection refused"
**Solution**: Check if PostgreSQL service is running

### Java/Maven Issues

**Issue**: "JAVA_HOME not set"
**Solution**: Set JAVA_HOME environment variable

**Issue**: "mvn: command not found"
**Solution**: Add Maven bin directory to PATH

## 9. What's Next?

After installing all prerequisites:

1. **Clone the repository** (if not already done):
   ```cmd
   git clone https://github.com/mitkumar1/user-management-api.git
   cd user-management-api
   ```

2. **Switch to feature branch**:
   ```cmd
   git checkout feature/profile-enhancements
   ```

3. **Start the application**:
   ```cmd
   start-monitoring-postgres.bat
   ```

4. **Access services**:
   - Application: http://localhost:8080
   - PostgreSQL: localhost:5432
   - pgAdmin: http://localhost:8082
   - Grafana: http://localhost:3000
   - SonarQube: http://localhost:9000

## 10. Support

If you encounter issues:

1. Check the troubleshooting section above
2. Verify all prerequisites are installed
3. Check Docker Desktop is running
4. Review logs: `docker logs <container-name>`
5. Restart services: `docker compose restart`

**Happy coding! 🚀**
