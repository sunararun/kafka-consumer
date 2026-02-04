# Use a base image with Java Development Kit (JDK)
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file into the container
# Replace 'your-application.jar' with the actual name of your built JAR file
COPY target/*.jar kafka-con-app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 9090

# Define the command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "kafka-con-app ..jar"]