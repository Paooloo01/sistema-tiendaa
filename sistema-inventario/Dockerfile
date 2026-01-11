# 1. ETAPA DE CONSTRUCCIÓN
# Usamos una imagen de Maven reciente para compilar
FROM maven:3.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. ETAPA DE EJECUCIÓN
# Usamos Java 21 (LTS) que es compatible con lo que programaste en el 25
FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /target/tienda.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]