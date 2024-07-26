FROM openjdk:17-jdk-slim
LABEL authors="lach_hamza"

WORKDIR /app

COPY target/mower-app-0.0.1-SNAPSHOT.jar mower-app.jar
COPY src/main/resources/instructions instructions

# Default command to run the JAR file, using environment variables
ENTRYPOINT ["java", "-jar", "mower-app.jar", "--spring.batch.job.enabled=true"]
