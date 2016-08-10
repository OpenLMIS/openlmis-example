FROM anapsix/alpine-java:jre8

COPY build/libs/*.jar /service.jar
COPY *.gpg /
RUN apk update && \
        apk update wget && \
        apk add ca-certificates wget && \
        update-ca-certificates
RUN wget https://github.com/xordataexchange/crypt/releases/download/v0.0.1/crypt-0.0.1-linux-amd64
RUN mv crypt-0.0.1-linux-amd64 /usr/local/bin/crypt && \
        chmod +x /usr/local/bin/crypt


EXPOSE 8080
CMD java $JAVA_OPTS -classpath extensions/*:service.jar org.springframework.boot.loader.JarLauncher && java $JAVA_OPTS -jar service.jar
