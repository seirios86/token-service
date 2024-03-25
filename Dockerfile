FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY ./build/libs/token-service-0.1.0.jar /app/

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "token-service-0.1.0.jar"]
