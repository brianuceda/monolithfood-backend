# Usar la imagen base de OpenJDK 17
FROM openjdk:17.0.7-jdk-slim

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/MonolithFoodApplication-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]