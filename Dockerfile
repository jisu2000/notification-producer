# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

# Copy Maven wrapper and pom
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Download dependencies (optional optimization)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the project (skip tests for faster build)
RUN ./mvnw package -DskipTests

# Stage 2: Create runtime image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Create certs directory
RUN mkdir -p /app/certs

# Copy truststore to certs directory
COPY src/main/resources/client.truststore.jks /app/certs/client.truststore.jks

# Expose application port
EXPOSE 7001

# Start the application
CMD ["java", "-jar", "app.jar"]
