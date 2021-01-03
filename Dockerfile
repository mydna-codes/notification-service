FROM openjdk:11-jre-slim

ENV KUMULUZEE_VERSION=not_set
ENV KUMULUZEE_ENV_NAME=dev
ENV KUMULUZEE_ENV_PROD=false
ENV KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://localhost:5432/notification-service
ENV KUMULUZEE_DATASOURCES0_USERNAME=postgres
ENV KUMULUZEE_DATASOURCES0_PASSWORD=postgres

RUN mkdir /app
WORKDIR /app

ADD ./api/target/notification-service.jar /app

EXPOSE 8080

CMD ["java", "-jar", "notification-service.jar", "com.kumuluz.ee.EeApplication"]