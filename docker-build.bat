@echo off
chcp 65001 >nul

echo 🚀 Building and running Guidely Exhibition Service with Docker...

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker first.
    pause
    exit /b 1
)

REM Build the Docker image
echo 🔨 Building Docker image...
docker build -t guidely-exhibition-service .

if %errorlevel% neq 0 (
    echo ❌ Docker build failed!
    pause
    exit /b 1
)

echo ✅ Docker image built successfully!

REM Run with docker-compose
echo 🐳 Starting services with docker-compose...
docker-compose up -d

if %errorlevel% neq 0 (
    echo ❌ Docker-compose failed!
    pause
    exit /b 1
)

echo ✅ Services started successfully!

REM Wait for services to be ready
echo ⏳ Waiting for services to be ready...
timeout /t 30 /nobreak >nul

REM Check service health
echo 🏥 Checking service health...
docker-compose ps

echo.
echo 🎉 Guidely Exhibition Service is now running!
echo 📍 Service URL: http://localhost:8082
echo 📍 MySQL: localhost:3306
echo 📍 Redis: localhost:6379
echo.
echo 📋 Useful commands:
echo   - View logs: docker-compose logs -f
echo   - Stop services: docker-compose down
echo   - Rebuild and restart: docker-compose up -d --build
echo.
echo 🔍 Test the API with: http://localhost:8082/actuator/health

pause 