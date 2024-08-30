FROM openjdk:17-jdk-slim
WORKDIR /app
COPY  target/*.jar ./app.jar
# Set the command to run the application
CMD ["java", "-jar", "app.jar"]
