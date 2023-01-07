FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /opt/service-registry.jar

ENTRYPOINT ["java","-jar", "/opt/service-registry.jar"]

EXPOSE 8761
