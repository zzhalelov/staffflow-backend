# Amazon Corretto JDK 17 на Alpine
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Копируем jar-файл
COPY target/*-SNAPSHOT.jar app.jar

# Проброс порта сервера
EXPOSE 8081

# Запуск приложения с профилем docker
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
