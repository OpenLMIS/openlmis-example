FROM anapsix/alpine-java:jre8

COPY build/libs/* /libs/

RUN mv $(find libs/ -name "*.jar" -not -name "*javadoc.jar" -not -name "*sources.jar" -type f) service.jar
RUN rm -r libs/

EXPOSE 8080
CMD java $JAVA_OPTS -classpath extensions/*:service.jar org.springframework.boot.loader.JarLauncher && java $JAVA_OPTS -jar service.jar
