# Usa la imagen base de la versión 21
FROM maven:3.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Ejecuta con Java 21
FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /target/tienda.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]