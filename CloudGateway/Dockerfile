FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /opt/cloud-gateway.jar

ENTRYPOINT ["java","-jar", "/opt/cloud-gateway.jar"]

EXPOSE 9090
