#!/bin/bash

echo "🚀 Starting User Management API Docker Deployment..."
echo "=================================================="

# Check current directory
echo "📁 Current directory:"
pwd

# List project files
echo "📋 Project files:"
ls -la

echo ""
echo "🔨 Building Docker image..."
docker build -t user-management-api:latest .

if [ $? -eq 0 ]; then
    echo "✅ Docker image built successfully!"
    
    echo ""
    echo "🚀 Starting application with Docker Compose..."
    docker-compose up -d
    
    if [ $? -eq 0 ]; then
        echo "✅ Application started successfully!"
        
        echo ""
        echo "📊 Container status:"
        docker ps
        
        echo ""
        echo "🌐 Your API is now running!"
        echo "   Application: Check the Ports panel in VS Code for the forwarded URL"
        echo "   H2 Console: {forwarded-url}/h2-console"
        echo ""
        echo "🧪 Test commands:"
        echo "   docker-compose logs -f    # View logs"
        echo "   docker ps                 # Check containers"
        echo "   docker-compose down       # Stop application"
        
    else
        echo "❌ Failed to start application"
        exit 1
    fi
else
    echo "❌ Failed to build Docker image"
    exit 1
fi

echo ""
echo "🎉 Deployment complete! Your User Management API is ready!"
