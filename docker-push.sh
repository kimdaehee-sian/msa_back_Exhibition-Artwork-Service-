#!/bin/bash

# Guidely Exhibition Service Docker Hub Push Script

# Docker Hub 설정
DOCKER_USERNAME="yerak213"
IMAGE_NAME="guidely-exhibition-service"
VERSION=${1:-latest}

echo "🚀 Pushing Guidely Exhibition Service to Docker Hub..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if user is logged in to Docker Hub
if ! docker info | grep -q "Username"; then
    echo "❌ You are not logged in to Docker Hub."
    echo "Please run: docker login"
    exit 1
fi

# Build the image with proper tag
echo "🔨 Building Docker image with tag: ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}"
docker build -t ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION} .

if [ $? -ne 0 ]; then
    echo "❌ Docker build failed!"
    exit 1
fi

# Also tag as latest if version is not latest
if [ "$VERSION" != "latest" ]; then
    echo "🏷️  Tagging as latest..."
    docker tag ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION} ${DOCKER_USERNAME}/${IMAGE_NAME}:latest
fi

echo "✅ Docker image built successfully!"

# Push to Docker Hub
echo "📤 Pushing to Docker Hub..."
docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}

if [ $? -ne 0 ]; then
    echo "❌ Push failed!"
    exit 1
fi

# Push latest tag if version is not latest
if [ "$VERSION" != "latest" ]; then
    echo "📤 Pushing latest tag..."
    docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:latest
fi

echo ""
echo "🎉 Successfully pushed to Docker Hub!"
echo "📍 Image: ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}"
echo "📍 Latest: ${DOCKER_USERNAME}/${IMAGE_NAME}:latest"
echo ""
echo "🔍 View on Docker Hub: https://hub.docker.com/r/${DOCKER_USERNAME}/${IMAGE_NAME}"
echo ""
echo "📋 Pull command:"
echo "  docker pull ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}"
echo ""
echo "📋 Run command:"
echo "  docker run -p 8082:8082 ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}" 