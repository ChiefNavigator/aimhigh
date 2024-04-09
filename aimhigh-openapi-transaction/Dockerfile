FROM openjdk:19

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /

ENTRYPOINT ["java", "-jar", "/aimhigh-openapi-transaction-0.0.1-SNAPSHOT.jar"]