# PowerShell Installation Script for User Management API Prerequisites
# Run as Administrator

param(
    [switch]$SkipDocker,
    [switch]$SkipPostgreSQL,
    [switch]$Minimal
)

# Colors
$Red = [System.ConsoleColor]::Red
$Green = [System.ConsoleColor]::Green
$Yellow = [System.ConsoleColor]::Yellow
$Blue = [System.ConsoleColor]::Blue

function Write-ColorHost($Message, $Color = [System.ConsoleColor]::White) {
    Write-Host $Message -ForegroundColor $Color
}

function Test-Administrator {
    $currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

function Install-Chocolatey {
    if (!(Get-Command choco -ErrorAction SilentlyContinue)) {
        Write-ColorHost "📦 Installing Chocolatey package manager..." $Yellow
        Set-ExecutionPolicy Bypass -Scope Process -Force
        [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
        try {
            Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
            Write-ColorHost "✅ Chocolatey installed successfully" $Green
            # Refresh environment
            $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
        }
        catch {
            Write-ColorHost "❌ Failed to install Chocolatey: $($_.Exception.Message)" $Red
            return $false
        }
    } else {
        Write-ColorHost "✅ Chocolatey is already installed" $Green
    }
    return $true
}

function Test-Command($Command) {
    try {
        Get-Command $Command -ErrorAction Stop | Out-Null
        return $true
    }
    catch {
        return $false
    }
}

function Install-Software($Name, $Package, $TestCommand) {
    Write-ColorHost "🔍 Checking for $Name..." $Blue
    
    if (Test-Command $TestCommand) {
        Write-ColorHost "✅ $Name is already installed" $Green
        return $true
    }
    
    Write-ColorHost "📦 Installing $Name..." $Yellow
    try {
        Start-Process "choco" -ArgumentList "install", $Package, "-y" -Wait -NoNewWindow
        
        # Refresh environment
        $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
        
        if (Test-Command $TestCommand) {
            Write-ColorHost "✅ $Name installed successfully" $Green
            return $true
        } else {
            Write-ColorHost "⚠️ $Name installed but command not found. May need restart." $Yellow
            return $true
        }
    }
    catch {
        Write-ColorHost "❌ Failed to install $Name: $($_.Exception.Message)" $Red
        return $false
    }
}

# Main installation script
Write-ColorHost "🚀 User Management API - Prerequisites Installation" $Green
Write-ColorHost "=================================================" $Green

# Check if running as Administrator
if (!(Test-Administrator)) {
    Write-ColorHost "❌ This script must be run as Administrator" $Red
    Write-ColorHost "Right-click on PowerShell and select 'Run as Administrator'" $Yellow
    exit 1
}

Write-ColorHost "✅ Running as Administrator" $Green

# Install Chocolatey
if (!(Install-Chocolatey)) {
    Write-ColorHost "❌ Cannot proceed without Chocolatey" $Red
    exit 1
}

# Install Java
if (!(Install-Software "Java JDK 17" "openjdk17" "java")) {
    Write-ColorHost "❌ Java installation failed" $Red
    exit 1
}

# Install Maven
if (!(Install-Software "Maven" "maven" "mvn")) {
    Write-ColorHost "❌ Maven installation failed" $Red
    exit 1
}

# Install Git
if (!(Install-Software "Git" "git" "git")) {
    Write-ColorHost "❌ Git installation failed" $Red
    exit 1
}

# Install Docker (optional)
if (!$SkipDocker) {
    if (!(Install-Software "Docker Desktop" "docker-desktop" "docker")) {
        Write-ColorHost "⚠️ Docker installation failed - you can install manually" $Yellow
    }
} else {
    Write-ColorHost "⏭️ Skipping Docker installation" $Yellow
}

# Install PostgreSQL (optional)
if (!$SkipPostgreSQL -and !$Minimal) {
    if (!(Install-Software "PostgreSQL" "postgresql" "psql")) {
        Write-ColorHost "⚠️ PostgreSQL installation failed - you can use Docker version" $Yellow
    }
} else {
    Write-ColorHost "⏭️ Skipping PostgreSQL installation (will use Docker)" $Yellow
}

# Verification
Write-ColorHost "`n🔍 Verification:" $Blue
Write-ColorHost "===============" $Blue

$commands = @(
    @{Name="Java"; Command="java"; Args="-version"},
    @{Name="Maven"; Command="mvn"; Args="-version"},
    @{Name="Git"; Command="git"; Args="--version"}
)

if (!$SkipDocker) {
    $commands += @{Name="Docker"; Command="docker"; Args="--version"}
}

if (!$SkipPostgreSQL -and !$Minimal) {
    $commands += @{Name="PostgreSQL"; Command="psql"; Args="--version"}
}

foreach ($cmd in $commands) {
    try {
        $result = & $cmd.Command $cmd.Args 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-ColorHost "✅ $($cmd.Name): OK" $Green
        } else {
            Write-ColorHost "⚠️ $($cmd.Name): Command found but may need restart" $Yellow
        }
    }
    catch {
        Write-ColorHost "❌ $($cmd.Name): Not found" $Red
    }
}

# Next steps
Write-ColorHost "`n🎉 Installation completed!" $Green
Write-ColorHost "=========================" $Green

Write-ColorHost "`n📋 Next steps:" $Blue
Write-ColorHost "1. Restart your computer (recommended)" $Yellow
Write-ColorHost "2. Open a new PowerShell/Command Prompt" $Yellow
Write-ColorHost "3. Navigate to your project directory" $Yellow
Write-ColorHost "4. Run: start-monitoring-postgres.bat" $Yellow

if (!$SkipDocker) {
    Write-ColorHost "`n🐳 Docker Notes:" $Blue
    Write-ColorHost "- Start Docker Desktop after restart" $Yellow
    Write-ColorHost "- Wait for Docker engine to be ready (green status)" $Yellow
}

Write-ColorHost "`n📚 Documentation:" $Blue
Write-ColorHost "- Installation Guide: INSTALLATION-GUIDE.md" $Yellow
Write-ColorHost "- PostgreSQL Setup: POSTGRESQL-SETUP.md" $Yellow
Write-ColorHost "- Monitoring Setup: MONITORING-SETUP.md" $Yellow

Write-ColorHost "`n🌐 After setup, access:" $Blue
Write-ColorHost "- Application: http://localhost:8080" $Yellow
Write-ColorHost "- pgAdmin: http://localhost:8082" $Yellow
Write-ColorHost "- Grafana: http://localhost:3000" $Yellow
Write-ColorHost "- SonarQube: http://localhost:9000" $Yellow

Write-ColorHost "`n💡 If you encounter issues:" $Blue
Write-ColorHost "- Check INSTALLATION-GUIDE.md troubleshooting section" $Yellow
Write-ColorHost "- Verify all commands work after restart" $Yellow
Write-ColorHost "- Ensure Docker Desktop is running" $Yellow

Write-ColorHost "`nHappy coding! 🚀" $Green

# Prompt for restart
Write-ColorHost "`n🔄 Restart recommended to complete installation." $Yellow
$restart = Read-Host "Would you like to restart now? (y/N)"
if ($restart -eq 'y' -or $restart -eq 'Y') {
    Write-ColorHost "Restarting in 10 seconds..." $Yellow
    Start-Sleep -Seconds 10
    Restart-Computer -Force
}
