FROM openjdk:11-jre-slim

EXPOSE 8081

RUN mkdir /app

COPY *.jar /app/spring-boot-application.jar

CMD ["java","-jar","/app/spring-boot-application.jar"]
