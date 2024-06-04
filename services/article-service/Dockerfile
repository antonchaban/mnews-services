# Stage 1: Build stage
FROM eclipse-temurin:21-jdk-alpine as builder
# Set the working directory inside the container
WORKDIR /app
# Copy the build files
COPY pom.xml ./
COPY src ./src
# Install Maven
RUN apk add --no-cache maven
# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine
# Set the working directory inside the container
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar
# Expose the port the application runs on
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
