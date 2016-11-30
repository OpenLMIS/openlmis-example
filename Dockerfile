FROM anapsix/alpine-java:jre8

COPY build/libs/openlmis-example-0.0.1.jar /service.jar

EXPOSE 8080
CMD java $JAVA_OPTS -classpath extensions/*:service.jar org.springframework.boot.loader.JarLauncher && java $JAVA_OPTS -jar service.jar
