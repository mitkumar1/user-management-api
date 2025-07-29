# GitHub Codespace Docker Deployment Guide

## Your Codespace is Ready! 🚀

Your GitHub Codespace "special sniffle" is now active and configured for Docker development.

### Step 1: Access Your Codespace
1. Open your browser and go to: https://github.com/codespaces
2. Click on your active Codespace "special sniffle"
3. Wait for the browser-based VS Code to load

### Step 2: Build and Run with Docker

Once in the Codespace terminal, run these commands:

```bash
# Verify you're in the right directory
pwd
# Should show: /workspaces/user-management-api

# List project files
ls -la

# Build the Docker image
docker build -t user-management-api:latest .

# Start the application stack
docker-compose up -d

# Check running containers
docker ps

# View application logs
docker-compose logs -f user-management-api
```

### Step 3: Test Your API

Your Spring Boot API will be available at:
- **Application URL**: `https://[codespace-name]-8080.githubpreview.dev`
- **H2 Database Console**: `https://[codespace-name]-8080.githubpreview.dev/h2-console`

#### Test Endpoints:

1. **Register a User (POST)**:
```bash
curl -X POST https://[codespace-name]-8080.githubpreview.dev/api/auth/register \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123"
}'
```

2. **Login (POST)**:
```bash
curl -X POST https://[codespace-name]-8080.githubpreview.dev/api/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "password": "password123"
}'
```

3. **Get Profile (GET with JWT token)**:
```bash
curl -X GET https://[codespace-name]-8080.githubpreview.dev/api/users/profile \
-H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Step 4: Development Commands

```bash
# Stop containers
docker-compose down

# Rebuild after code changes
docker-compose down
docker build -t user-management-api:latest .
docker-compose up -d

# View all logs
docker-compose logs

# Access container shell
docker exec -it user-management-api-app sh
```

### Important Notes:

1. **Port Forwarding**: Codespace automatically forwards port 8080
2. **Persistence**: Your code changes are saved in the Codespace
3. **Environment**: This is a cloud-based development environment with full Docker support
4. **Database**: H2 database data is persistent within the container lifecycle

### Troubleshooting:

If you encounter issues:
```bash
# Check container status
docker ps -a

# View container logs
docker logs user-management-api-app

# Restart services
docker-compose restart

# Clean rebuild
docker-compose down
docker system prune -f
docker build -t user-management-api:latest .
docker-compose up -d
```

## Next Steps:
1. Access your Codespace
2. Run the Docker build commands
3. Test your API endpoints
4. Start developing in the cloud! ☁️

Your User Management API is ready for cloud-based development and testing!
