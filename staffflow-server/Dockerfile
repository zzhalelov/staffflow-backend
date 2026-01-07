# ---------------------------
# Stage 1: Build with Maven
# ---------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Сначала копируем pom.xml и скачиваем зависимости (кэширование слоёв)
COPY pom.xml .
RUN staffflow-server/pom.xml staffflow-server/pom.xml

# Скачиваем зависимости
RUN mvn -B -pl staffflow-server -am dependency:go-offline

# Копируем исходники
COPY staffflow-server staffflow-server

# Сборка
RUN mvn -B -pl staffflow-server -am -DskipTests package

# ---------------------------
# Stage 2: Runtime
# ---------------------------
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Копируем собранный jar из предыдущего этапа
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

# Проброс порта
EXPOSE 8081

# Запуск приложения с профилем Docker
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
