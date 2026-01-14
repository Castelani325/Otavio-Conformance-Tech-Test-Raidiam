FROM gradle:8.10-jdk17 AS builder
COPY --chown=gradle:gradle . /authserver
WORKDIR /authserver
RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:17.0.13_11-jdk-ubi9-minimal
FROM gcr.io/distroless/java17-debian12

ARG JAR_FILE=/authserver/build/libs/authserver-0.0.1-SNAPSHOT.jar
COPY --from=builder ${JAR_FILE} auth.jar
#COPY ${JAR_FILE} auth.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/auth.jar"]
