FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/customer-data-api-1.0.0.jar customer-data-api-1.0.0.jar
EXPOSE 8081
LABEL authors="spalla"

ENTRYPOINT ["java", "-jar", "customer-data-api-1.0.0.jar", "--spring.profiles.active=prod"]