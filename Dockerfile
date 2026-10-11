FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]