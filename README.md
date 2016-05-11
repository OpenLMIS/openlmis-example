# OpenLMIS Example Service
This example is meant to show an OpenLMIS 3.x Independent Service at work.

## Prerequisites
* Docker 1.11+

## Quick Start
1. Fork/clone this repository from GitHub.

 ```shell
 git clone https://github.com/OpenLMIS/openlmis-template-service.git <openlmis-yourServiceName>
 ```
2. To properly test this example service is working, enter `spring.mail` values in the `application.properties` file.
3. Develop w/ Docker by running `docker-compose run --service-ports example`.  
See [Developing w/ Docker](#devdocker).
4. Run the migrations, using `gradle flywayMigrate`.
5. To start the Spring Boot application, run with: `gradle bootRun`.
6. Go to `http://<yourDockerIPAddress>:8080/api/` to see the APIs.
7. To test that email notifications are working in the example service, POST to:

 `http://<yourDockerIPAddress>:8080/api/notifications`
 
 some JSON object like so (put Content-Type: application/json in the headers):
 
 ```
 { "recipient": "<aValidEmailAddress>", "subject": "Test message", "message": "This is a test." }
 ```