FROM openjdk:8-jre-slim
MAINTAINER Bill Soumakis

WORKDIR /var/spices-inventory

ADD target/inventory-1.0-SNAPSHOT.jar /var/spices-inventory/inventory.jar

EXPOSE 9090 9091

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "inventory.jar"]