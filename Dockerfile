FROM openjdk:latest

ADD target/person-spring-boot-api-0.0.1-SNAPSHOT.jar person-spring-boot-api.jar

ENTRYPOINT ["java","-jar","person-spring-boot-api.jar"]

EXPOSE 8080