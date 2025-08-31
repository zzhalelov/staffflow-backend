FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/*-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]