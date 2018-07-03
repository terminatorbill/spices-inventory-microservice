FROM openjdk:8-jre-slim
MAINTAINER Bill Soumakis

WORKDIR /var/spices-inventory

ADD target/inventory-1.0-SNAPSHOT.jar /var/spices-inventory/inventory.jar

EXPOSE 9090 9091 5005

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "-Dspring.profiles.active=prod", "inventory.jar"]