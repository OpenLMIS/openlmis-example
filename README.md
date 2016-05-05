# OpenLMIS Example Service
This example is meant to show an OpenLMIS 3.x Independent Service at work.

## Prerequisites
* Docker
* PostgreSQL

## Quick Start
1. Fork/clone this repository from GitHub.

 ```
 git clone https://github.com/OpenLMIS/openlmis-template-service.git <openlmis-yourServiceName>
 ```

2. To properly test this example service is working, enter `spring.mail` values in the `application.properties` file.
2. Build the docker image using the Dockerfile provided. An example command:

 ```
 docker build -t "openlmis/openlmis-example" .
 ```

3. Before running the container, make sure you have a PostgreSQL docker container running with the proper configuration. For example:

 ```
 docker run -d -p 5432:5432 --name openlmis-db -e POSTGRES_PASSWORD=p@ssw0rd -e POSTGRES_DB=open_lmis postgres:9.5
 ```

4. Run the migrations.

 ```
 docker run --rm --link openlmis-db openlmis/openlmis-example gradle flywayMigrate
 ```

5. Run the container for the image you just built.

 ```
 docker run -d -p 32772:8080 --name openlmis-example --link openlmis-db openlmis/openlmis-example
 ```

6. Go to `http://<yourDockerIPAddress>:32772/api/` to see the APIs.
7. To test that email notifications are working in the example service, POST to:

 `http://<yourDockerIPAddress>:32772/api/notifications`
 
 some JSON object like so (put Content-Type: application/json in the headers):
 
 ```
 { "recipient": "<aValidEmailAddress>", "subject": "Test message", "message": "This is a test." }
 ```