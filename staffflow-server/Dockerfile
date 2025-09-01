FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/*-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]