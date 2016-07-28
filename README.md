# OpenLMIS Example Service
This example is meant to show an OpenLMIS 3.x Independent Service at work.

## Prerequisites
* Docker 1.11+

## Quick Start
1. Fork/clone this repository from GitHub.

 ```shell
 git clone https://github.com/OpenLMIS/openlmis-example.git
 ```
2. To properly test this example service is working, enter `spring.mail` values in the `application.properties` file.
3. Add an environment file called `.env` to the root folder of the project, with the required 
project settings and credentials. For a starter environment file, you can use [this 
one](https://github.com/OpenLMIS/openlmis-config/blob/master/.env).
4. Develop w/ Docker by running `docker-compose run --service-ports example`.
See [Developing w/ Docker](#devdocker).
5. To start the Spring Boot application, run with: `gradle bootRun`.
6. Go to `http://<yourDockerIPAddress>:8080/api/` to see the APIs.

## Email notifications
To test that email notifications are working in the example service, POST to:

 `http://<yourDockerIPAddress>:8080/api/notifications`

 some JSON object like so (put Content-Type: application/json in the headers):

 ```
 { "recipient": "<aValidEmailAddress>", "subject": "Test message", "message": "This is a test." }
 ```

## Customizing REST controls
This example service also demonstrates how to enable/disable RESTful actions.
Two classes, Foo and ReadOnlyFooRepository (in conjunction with Spring Data
REST), expose a REST endpoint at /api/foos. This endpoint is read-only, which
means POST/PUT/DELETE actions are not allowed. The ReadOnlyFooRepository does
this by extending the Repository interface, rather than the CrudRepository
interface, and defining the methods allowed. For read-only access, the save()
and delete() methods, which are present in the CrudRepository, are not defined
in the ReadOnlyFooRepository. By selectively exposing CRUD methods using Spring
Data JPA, REST controls can be customized.

See the [Spring Data JPA reference](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.definition-tuning)
for more information.

## API Definition and Testing
Our API is defined using RAML. This repository offers a preferred approach for integration-testing the API, generating user-friendly documentation for it, and ensuring that it’s congruous with the specification defined within the project’s RAML.

Specifically, `api-definition.yaml` contains the project’s RAML. As `BookIntegrationTest.java` illustrates, RestAssured and raml-tester are paired in order to test the API's functionality and to ensure that it matches the specification within `api-definition.yaml`.

After running `gradle ramlToSwagger bootRun`, developers can browse to `http://<yourDockerIPAddress>:8080` to see a user-friendly and interactive version of the API spec.

*Note that `api-definition.yaml` and `BookIntegrationTest.java` both currently contain the hardcoded address 192.168.99.100. This is a known inconvenience intended to be addressed soon.*

## Data Validation
The repository also illustrates two commonly used approaches to data validation within the context of Spring Rest: annotations and the validator pattern. Both work. Because the annotation-based approach is more prevalent within modern projects, however, the OpenLMIS team suggests adopting it within yours.

In the sample repository, the following classes comprise an example of the annotation-based approach:

 - **Bar.java** – Makes use of the following validation-related annotations: @Min, @Max, @NotEmpty, @Column(unique=true), and
   @BarValidation. The @BarValidation is an example of a
   custom-validation, and one which happens to perform cross-member
   checks.

 - **BarValidation.java** – Defines a custom @BarValidation annotation.

 - **BarValidator.java** – A class used by the @BarValidation annotation to perform the actual validation.

The following classes comprise an example of the alternate, validator-pattern, approach:

- **NotificationValidator.java** – Provides validation logic for the Notification class.

- **NotificationController.java** – Manually calls the aforementioned NotificationValidator in order to perform validation.

## Security
This example includes oauth2 resource server configuration that validates tokens with external auth container.

By default, the authorization server runs on port `8081`. To obtain a token, make a request to the endpoint at `/oauth/token`.
The client credentials must be included in Authorization header.

An example with `password` grant type:

    POST http://localhost:8081/oauth/token?grant_type=password&username=admin&password=password
    Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
     
    Basic Authentication
        Username
            trusted-client 
        Password
            secret

    Parameters
        grant_type
            Authorization grant type.
        username 
            The resource owner username.
        password 
            The resource owner password.
    
Response:
 
    {"access_token":"151a02ed-b6b4-4233-9566-cac2b7a1aec9","token_type":"bearer","expires_in":42509,"scope":"read write"}


Access the protected resource by adding access_token parameter to the request: 

    http://localhost:8080/api/foos/count?access_token=151a02ed-b6b4-4233-9566-cac2b7a1aec9

The application is configured to permit all by default. It can be restricted through annotations:
`@PreAuthorize("hasAuthority('USER') and #oauth2.hasScope('read')")` used in **FooController.java**
`@PreAuthorize("hasAuthority('ADMIN')")` used in **WeatherController.java**

## Building & Testing

Gradle is our usual build tool.  This template includes common tasks
that most Services will find useful:

- `clean` to remove build artifacts
- `build` to build all source. `build`, after building sources, also runs unit tests. Build will be successful only if all tests pass.
- `generateMigration -PmigrationName=<yourMigrationName>` to create a
"blank" database migration script. A file
will be generated under `src/main/resources/db/migration`. Put your
migration SQL into that file.
- `test` to run unit tests
- `integrationTest` to run integration tests
- `sonarqube` to execute the SonarQube analysis.

The **test results** are shown in the console.

While Gradle is our usual build tool, OpenLMIS v3+ is a collection of
Independent Services where each Gradle build produces 1 Service.
To help work with these Services, we use Docker to develop, build and
publish these.

See [Developing with Docker](#devdocker).

## <a name="extensions"></a> Extension points and extension modules

OpenLMIS allows extending or overriding certain behavior of service using extension points and extension modules.
Every independent service can expose extension points that an extension module may utilize to extend its behavior.
Extension point is a Java interface placed in the service. Every extension point has its default implementation that
can be overriden. Each default implementation of given extension point has `@DefaultImplementation` annotation.
Extension modules can contain custom implementation of one or more extension points from main service.

Decision about which implementation should be used is made based on configuration file `extensions.xml`.
If there is no extension defined for given extension point, default implementation will be used.

Configuration file should be placed in `ect/openlmis` directory. This directory can be changed, it is defined in `docker-compose.yml` file.
Every extension module should be deployed as JAR and placed in directory `etc/openlmis/extensions` (this is also defined in `docker-compose.yml`).

Example extension module and configuration file will be soon published in the new repository `openlmis-example-extension`.

In the sample repository, the following classes are example of extension points usage:

- **OrderQuantity.java** - sample extension point, that has getInfo method.
- **DefaultOrderQuantity.java** - default implementation of that interface, it has `@DefaultImplementation` annotation.
- **ExtensionManager.java** - class that has getImplementation method. It returns implementation of given extension point.

Endpoint using extension point is defined in **MessageController.java**:
    http://localhost:8080/extensionPoint

This endpoint provides information which class was returned as OrderQuantity implementation and what was
the result of "getInfo" method defined by OrderQuantity interface.

## <a name="devdocker"></a> Developing with Docker

OpenLMIS utilizes Docker to help with development, building, publishing
and deployment of OpenLMIS Services. This helps keep development to
deployment environments clean, consistent and reproducible and
therefore using Docker is recommended for all OpenLMIS projects.

To enable development in Docker, OpenLMIS publishes a couple Docker
Images:

- [openlmis/dev](https://hub.docker.com/r/openlmis/dev/) - for Service
development.  Includes the JDK & Gradle plus common build tools.
- [openlmis/postgres](https://hub.docker.com/r/openlmis/postgres/) - for
quickly standing up a shared PostgreSQL DB

In addition to these Images, each Service includes Docker Compose
instructions to:

- standup a development environment (run Gradle)
- build a lean image of itself suitable for deployment
- publish its deployment image to a Docker Repository

### Development Environment
Launches into shell with Gradle & JDK available suitable for building
Service.  PostgreSQL connected suitable for testing. If you run the
Service, it should be available on port 8080.

Before starting the development environment, make sure you have a `.env` file as outlined in the 
Quick Start instructions.

```shell
> docker-compose run --service-ports <serviceName>
$ gradle clean build
$ gradle bootRun
```

### Build Deployment Image
The specialized docker-compose.builder.yml is geared toward CI and build
servers for automated building, testing and docker image generation of
the service.

Before building the deployment image, make sure you have a `.env` file as outlined in the Quick
Start instructions.

```shell
> docker-compose -f docker-compose.builder.yml run builder
> docker-compose -f docker-compose.builder.yml build image
```

### Publish to Docker Repository
TODO

### Docker's file details
A brief overview of the purpose behind each docker related file

- `Dockerfile`:  build a deployment ready image of this service
suitable for publishing.
- `docker-compose.yml`:  base docker-compose file.  Defines the
basic composition from the perspective of working on this singular
vertical service.  These aren't expected to be used in the
composition of the Reference Distribution.
- `docker-compose.override.yml`:  extends the `docker-compose.yml`
base definition to provide for the normal usage of docker-compose
inside of a single Service:  building a development environment.
Wires this Service together with a DB for testing, a gradle cache
volume and maps tomcat's port directly to the host.
- `docker-compose.builder.yml`:  an alternative docker-compose file
suitable for CI type of environments to test & build this Service
and generate a publishable/deployment ready Image of the service.

### Logging
See the Logging section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md.

### Internationalization (i18n)
See the Internationalization section in the Service Template README at 
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md.

### Debugging
See the Debugging section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md.
