FROM maven AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim
# Set the working directory in the container
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
# Set the command to run the application
CMD ["java", "-jar", "app.jar"]
