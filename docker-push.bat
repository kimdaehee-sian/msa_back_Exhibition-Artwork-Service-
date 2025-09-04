@echo off
chcp 65001 >nul

REM Guidely Exhibition Service Docker Hub Push Script

REM Docker Hub 설정
set DOCKER_USERNAME=yerak213
set IMAGE_NAME=guidely-exhibition-service
set VERSION=%1
if "%VERSION%"=="" set VERSION=latest

echo 🚀 Pushing Guidely Exhibition Service to Docker Hub...

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker first.
    pause
    exit /b 1
)

REM Check if user is logged in to Docker Hub
docker info | findstr "Username" >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Could not verify Docker Hub login status, but continuing...
    echo Please ensure you are logged in with: docker login
    echo.
)

REM Build the image with proper tag
echo 🔨 Building Docker image with tag: %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%
docker build -t %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION% .

if %errorlevel% neq 0 (
    echo ❌ Docker build failed!
    pause
    exit /b 1
)

REM Also tag as latest if version is not latest
if not "%VERSION%"=="latest" (
    echo 🏷️  Tagging as latest...
    docker tag %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION% %DOCKER_USERNAME%/%IMAGE_NAME%:latest
)

echo ✅ Docker image built successfully!

REM Push to Docker Hub
echo 📤 Pushing to Docker Hub...
docker push %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%

if %errorlevel% neq 0 (
    echo ❌ Push failed!
    pause
    exit /b 1
)

REM Push latest tag if version is not latest
if not "%VERSION%"=="latest" (
    echo 📤 Pushing latest tag...
    docker push %DOCKER_USERNAME%/%IMAGE_NAME%:latest
)

echo.
echo 🎉 Successfully pushed to Docker Hub!
echo 📍 Image: %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%
echo 📍 Latest: %DOCKER_USERNAME%/%IMAGE_NAME%:latest
echo.
echo 🔍 View on Docker Hub: https://hub.docker.com/r/%DOCKER_USERNAME%/%IMAGE_NAME%
echo.
echo 📋 Pull command:
echo   docker pull %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%
echo.
echo 📋 Run command:
echo   docker run -p 8082:8082 %DOCKER_USERNAME%/%IMAGE_NAME%:%VERSION%

pause 