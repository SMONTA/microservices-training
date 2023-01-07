FROM openjdk:11

ARG JAR_FILE=target/*.jar
ARG ENV_DOCKER_PATH=src/main/resources/env-docker

COPY ${JAR_FILE} /opt/config-server.jar
COPY ${ENV_DOCKER_PATH}/application.yml /opt/application.yml
COPY ${ENV_DOCKER_PATH}/external-config.yml /opt/external-config.yml

ENTRYPOINT ["java","-jar", "/opt/config-server.jar"]

EXPOSE 9296
