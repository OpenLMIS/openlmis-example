FROM openlmis/service-base

COPY build/libs/*.jar /service.jar
COPY build/consul /consul
