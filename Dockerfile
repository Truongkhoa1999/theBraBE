#
# Build stage
#
FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/thebra-0.0.1-SNAPSHOT.jar thebra.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","fs14-backend.jar"]
