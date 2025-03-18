# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-alpine
WORKDIR /
COPY --from=build /target/challenge-0.0.1-SNAPSHOT.jar ./challenge.jar
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=beta", "-jar", "challenge.jar"]