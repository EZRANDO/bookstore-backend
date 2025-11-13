FROM gradle:8.2-jdk21 AS builder
WORKDIR /app
COPY src/build .
RUN gradle clean build -x test

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]