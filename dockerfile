# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Container working directory
WORKDIR /app

COPY build/libs/taskmanager-0.0.1-SNAPSHOT.jar /app/taskmanager-0.0.1-SNAPSHOT.jar

EXPOSE 8084

# Run the JAR file
ENTRYPOINT ["java","-jar","/app/taskmanager-0.0.1-SNAPSHOT.jar"]