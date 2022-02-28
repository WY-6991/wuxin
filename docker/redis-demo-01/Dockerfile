FROM openjdk:8-jdk-slim
LABEL maintainer=wuxin001
COPY target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]