# Use the official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application jar file
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 3000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=dev"]