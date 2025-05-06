FROM openjdk:21
LABEL authors="David Mora Duque"
WORKDIR /app
COPY target/MicroServicioMensajeria-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "MicroServicioMensajeria-0.0.1-SNAPSHOT.jar"]