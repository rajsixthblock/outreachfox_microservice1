FROM openjdk:8
ADD target/company-service-0.0.1-SNAPSHOT.jar company-service.jar
ENTRYPOINT ["java","-jar","company-service.jar"]