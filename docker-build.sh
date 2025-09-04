#!/bin/bash

# Guidely Exhibition Service Docker Build & Run Script

echo "🚀 Building and running Guidely Exhibition Service with Docker..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Build the Docker image
echo "🔨 Building Docker image..."
docker build -t guidely-exhibition-service .

if [ $? -ne 0 ]; then
    echo "❌ Docker build failed!"
    exit 1
fi

echo "✅ Docker image built successfully!"

# Run with docker-compose
echo "🐳 Starting services with docker-compose..."
docker-compose up -d

if [ $? -ne 0 ]; then
    echo "❌ Docker-compose failed!"
    exit 1
fi

echo "✅ Services started successfully!"

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
echo "🏥 Checking service health..."
docker-compose ps

echo ""
echo "🎉 Guidely Exhibition Service is now running!"
echo "📍 Service URL: http://localhost:8082"
echo "📍 MySQL: localhost:3306"
echo "📍 Redis: localhost:6379"
echo ""
echo "📋 Useful commands:"
echo "  - View logs: docker-compose logs -f"
echo "  - Stop services: docker-compose down"
echo "  - Rebuild and restart: docker-compose up -d --build"
echo ""
echo "🔍 Test the API with: http://localhost:8082/actuator/health" 