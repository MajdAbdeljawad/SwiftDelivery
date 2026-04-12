FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/Gestion-de-livraison-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
