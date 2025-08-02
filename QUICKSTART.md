# Quick Start Guide

## Choose Your Setup Method

### 🐳 Option 1: Docker Setup (Recommended)

**Prerequisites**: Docker Desktop

```bash
# Install Docker first
install-docker.bat

# Then start everything
start-monitoring-postgres.bat
```

**Includes**: PostgreSQL, pgAdmin, Grafana, Prometheus, SonarQube

---

### 💻 Option 2: Native PostgreSQL (No Docker)

**Prerequisites**: PostgreSQL installed locally

```bash
# Start with native PostgreSQL
start-without-docker.bat
```

**Includes**: Just PostgreSQL and the application

---

### ⚡ Option 3: Automated Installation

**Prerequisites**: Administrator access

```powershell
# Install everything automatically
.\install-prerequisites.ps1

# Then restart and run
start-monitoring-postgres.bat
```

---

## Step-by-Step Instructions

### For Docker Setup:

1. **Install Docker Desktop**:
   ```bash
   install-docker.bat
   ```
   - Downloads and installs Docker Desktop
   - Restart computer after installation

2. **Start Docker Desktop**:
   - Look for Docker whale icon in system tray
   - Wait for it to turn green (ready)

3. **Start the application**:
   ```bash
   start-monitoring-postgres.bat
   ```

4. **Access services**:
   - Application: http://localhost:8080
   - pgAdmin: http://localhost:8082
   - Grafana: http://localhost:3000

### For Native PostgreSQL:

1. **Install PostgreSQL** (if not installed):
   - Download from: https://www.postgresql.org/download/windows/
   - Use password: `SecureP@ssw0rd123`
   - Port: `5432`

2. **Start the application**:
   ```bash
   start-without-docker.bat
   ```

3. **Access application**:
   - Application: http://localhost:8080
   - Health: http://localhost:8080/actuator/health

## Troubleshooting

### Docker Issues:
- **"Docker not found"**: Run `install-docker.bat`
- **"Docker daemon not running"**: Start Docker Desktop
- **"Port already in use"**: Stop other services on ports 5432, 8080, etc.

### PostgreSQL Issues:
- **"psql not found"**: Install PostgreSQL or add to PATH
- **"Connection refused"**: Check PostgreSQL service is running
- **"Authentication failed"**: Use correct password (SecureP@ssw0rd123)

### Application Issues:
- **Build fails**: Check Java 17+ and Maven are installed
- **Port 8080 in use**: Stop other applications using port 8080
- **Database connection error**: Verify PostgreSQL is running and accessible

## Testing Your Setup

### Test Database Connection:
```bash
test-postgres.bat
```

### Test Application:
```bash
# Health check
curl http://localhost:8080/actuator/health

# Register user
curl -X POST http://localhost:8080/api/v1/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

## Next Steps

1. **Explore the API**: Use the included Postman collection
2. **Monitor performance**: Check Grafana dashboards
3. **Code quality**: Run SonarQube analysis
4. **Development**: Create feature branches and PRs

## Support

- 📖 **Full documentation**: See `INSTALLATION-GUIDE.md`
- 🐘 **PostgreSQL help**: See `POSTGRESQL-SETUP.md`
- 📊 **Monitoring setup**: See `MONITORING-SETUP.md`
- 🤖 **Jenkins CI/CD**: See `JENKINS-MULTIBRANCH-SETUP.md`

---

**Ready to start coding! 🚀**
