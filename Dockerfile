FROM eclipse-temurin:17
LABEL author=shugar
ARG JAR_FILE=target/AutenticacionPerezosa-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_veterinaria.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app_veterinaria.jar"]
