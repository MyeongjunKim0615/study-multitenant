FROM openjdk:21-jdk-slim

CMD ["./mvnw","clean","package"]

ARG JAR_FILE_PATH=build/libs/project-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE_PATH} project-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "project-0.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=local"]