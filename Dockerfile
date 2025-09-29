# Stage 1: Build with Maven (Java 21)
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and compile
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (change if your app runs on another port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
