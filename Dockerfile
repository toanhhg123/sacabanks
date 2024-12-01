FROM maven:3.9.8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y fontconfig && apt-get install -y libfreetype6 && apt-get clean
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
CMD ["java", "-Djava.awt.headless=true" ,"-jar", "app.jar"]
