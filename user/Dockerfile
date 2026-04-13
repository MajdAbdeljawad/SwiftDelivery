# 1. Image de base Java 17
FROM eclipse-temurin:17-jdk

# 2. Dossier de travail dans le container
WORKDIR /app

# 3. Copier le fichier .jar depuis target vers le container
COPY target/user-0.0.1-SNAPSHOT.jar user.jar

# 4. Exposer le port de ton microservice
EXPOSE 8087

# 5. Lancer l'application Spring Boot
ENTRYPOINT ["java","-jar","user.jar"]