# ---------------------------
# Stage 1: Build
# ---------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /build

# 1. Копируем parent POM
COPY pom.xml .

# 2. Копируем POM модуля
COPY staffflow-server/pom.xml staffflow-server/pom.xml

# 3. Кешируем зависимости
RUN mvn -B -pl staffflow-server -am dependency:go-offline

# 4. Копируем исходники модуля
COPY staffflow-server staffflow-server

# 5. Сборка ТОЛЬКО server-модуля
RUN mvn -B -pl staffflow-server -am -DskipTests package


# ---------------------------
# Stage 2: Runtime
# ---------------------------
FROM amazoncorretto:17-alpine-jre

WORKDIR /app

COPY --from=build /build/staffflow-server/target/*-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
