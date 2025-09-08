# Build stage
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copy gradle wrapper first for better caching
COPY gradle gradle
COPY gradlew .
COPY gradle.properties* .
COPY settings.gradle .
COPY build.gradle .

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies for better caching
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Install curl for health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd -r spring && useradd -r -g spring spring

# Copy jar from build stage
COPY --from=builder /app/build/libs/*[!-plain].jar app.jar

# Change ownership
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"] 