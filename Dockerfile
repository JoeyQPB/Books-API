FROM azul/zulu-openjdk:17

WORKDIR /app

COPY target/Books-API-1.0.0.jar /app/bookApi.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/bookApi.jar"]