# 1. Usar una imagen base de Maven para construir la app
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Usar una imagen ligera de Java para ejecutarla
FROM openjdk:17-jdk-slim
COPY --from=build /target/tienda.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]