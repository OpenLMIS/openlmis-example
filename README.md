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

## <a name="api">API Definition and Testing</a>
Our API is defined using RAML. This repository offers a preferred approach for integration-testing the API, generating user-friendly documentation for it, and ensuring that it’s congruous with the specification defined within the project’s RAML.

Specifically, `src/main/resources/api-definition.yaml` contains the project’s RAML.

After running `gradle ramlToSwagger bootRun`, developers can browse to `http://<yourDockerIPAddress>:8080/index.html` to see a user-friendly and interactive version of the API spec.

NOTE: `api-definition.yaml` contains the project’s RAML. As `BookIntegrationTest.java` illustrates, RestAssured and raml-tester are paired in order to test the API's functionality and to ensure that it matches the specification within `api-definition.yaml`.

## Building & Testing
See the Building & Testing section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#building.

## <a name="security">Security</a>
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

When authentication succeeds, the user instance is stored as token principal.

NOTE: This example service is configured to permit all by default. It can be restricted through annotations:
`@PreAuthorize("hasAuthority('USER') and #oauth2.hasScope('read')")` used in **FooController.java**
`@PreAuthorize("hasAuthority('ADMIN')")` used in **WeatherController.java**

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

## <a name="extensions">Extension points and extension modules</a>
OpenLMIS allows extending or overriding certain behavior of service using extension points and extension modules.
Every independent service can expose extension points that an extension module may utilize to extend its behavior.
Extension point is a Java interface placed in the service. Every extension point has its default implementation that
can be overriden. Extension modules can contain custom implementation of one or more extension points from main service.

Decision about which implementation should be used is made based on configuration file `extensions.properties`.
Configuration file contains information about which extension should be used for which extension point.
There is default configuration file in this repository, that provides default implementations for all created extension points.
Example configuration file, where `OrderQuantity` is an extension point ID and `DefaultOrderQuatity` is an extension ID:

```
#Example extensions configuration
OrderQuantity=DefaultOrderQuantity
```

Configuration file lives in independent service repository. 
Every extension module should be deployed as JAR and placed in directory `etc/openlmis/extensions`.
This directory can be changed, it is defined in `docker-compose.yml` file.

Example extension module and configuration file will be soon published in the new repository `openlmis-example-extension`.
This repository can currently be find [here.](https://github.com/weronika-ciecierska/openlmis-example-extension)

Following classes are example of extension points usage:

- **OrderQuantity.java** - sample extension point, that has getInfo method and Id defined.
- **DefaultOrderQuantity.java** - default implementation of that interface, it has `@Component` annotation that contains its Id.
- **ExtensionManager.java** - class that has getExtensionByPointId method. It returns implementation of an extension class that is defined in
    configuration file for extension point with given Id.

Endpoint using extension point is defined in **MessageController.java**:
    http://localhost:8080/extensionPoint

This endpoint provides information which class was returned as OrderQuantity implementation and what was
the result of "getInfo" method defined by OrderQuantity interface.

## <a name="devdocker">Developing with Docker</a>
See the Developing with Docker section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#devdocker.

### Development Environment
See the Development Environment section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#devenv.

### Build Deployment Image
See the Build Deployment Image section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#buildimage.

### Publish to Docker Repository
TODO

### Docker's file details
See the Docker's file details section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#dockerfiles.

### Logging
See the Logging section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#logging.

### Internationalization (i18n)
See the Internationalization section in the Service Template README at 
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#internationalization.

### Debugging
See the Debugging section in the Service Template README at
https://github.com/OpenLMIS/openlmis-template-service/blob/master/README.md#debugging.
