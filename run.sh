#!/bin/sh
node consul/registration.js -c register -f consul/config.json -r consul/api-definition.yaml
java $JAVA_OPTS -classpath extensions/*:service.jar org.springframework.boot.loader.JarLauncher && java $JAVA_OPTS -jar service.jar
