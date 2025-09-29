# ------------------------------
# Stage 1: Build with Maven
# ------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cached if unchanged)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# ------------------------------
# Stage 2: Run with JRE
# ------------------------------
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (change if your app runs on another port)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
