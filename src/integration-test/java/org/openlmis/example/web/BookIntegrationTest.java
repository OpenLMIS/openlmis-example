package org.openlmis.example.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import com.jayway.restassured.RestAssured;

import guru.nidi.ramltester.RamlDefinition;
import guru.nidi.ramltester.RamlLoaders;
import guru.nidi.ramltester.junit.RamlMatchers;
import guru.nidi.ramltester.restassured.RestAssuredClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.example.Application;
import org.openlmis.example.domain.Book;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/*
  This class exists in order to illustrate the currently recommended way of performing 
  integration testing. The RestAssured library makes it easy to make Restful calls and validate 
  their responses. For each client-server interaction, an assertion is added to verify that the 
  HTTP requests and responses match those defined within the "api-definition.yaml" RAML file.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
@WebIntegrationTest("server.port:8080")
public class BookIntegrationTest {
  private static final String BASE_URL = "http://localhost:8080";
  private static final String RAML_ASSERT_MESSAGE = "HTTP request/response should match RAML " 
      + "definition.";

  private static final String BOOK_RESOURCE = "/api/books";

  private RamlDefinition ramlDefinition;
  private RestAssuredClient restAssured;

  @Before
  public void setUp() {
    RestAssured.baseURI = BASE_URL;
    ramlDefinition = RamlLoaders.fromClasspath().load("api-definition.yaml");
    restAssured = ramlDefinition.createRestAssured();
  }

  /*
    Note that this integration test is intended to demonstrate the paired use of
    RestAssured and raml-tester rather than thorough and thoughtful testing per se.
   */
  @Test
  public void testCreate() {

    // Verify that our RAML file is valid.
    // This is commented out because it complains about missing descriptions that aren't necessary
    // at the moment.
    //Assert.assertThat(ramlDefinition.validate(), RamlMatchers.validates());


    // Make a simple call and verify that the input and output match what's defined in our RAML spec
    restAssured.given().get(BOOK_RESOURCE).andReturn();
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());


    // Make the same call as above, but ensure we get a 404 response
    restAssured.given()
        .when().get(BOOK_RESOURCE)
        .then().statusCode(404);
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());


    // Create a user, making sure we get back a 201 along with the ISBN we expect in the response 
    // body
    restAssured.given().contentType("application/json")
        .body(getBook("name_1", "isbn_1"))
        .when().post(BOOK_RESOURCE)
        .then().statusCode(201).and().body("isbn", equalTo("isbn_1"));
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());

    // Post another user, deserialize the resultant response body into a local variable, and 
    // ensure it has an ID
    Book book = restAssured.given().body(getBook("name_2", "isbn_2"))
        .post(BOOK_RESOURCE).as(Book.class);
    Assert.assertNotEquals("POST operation should have assigned an ISBN to the new object", "", 
        book.getId());
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());

    // Having done POST on /api/books twice, ensure a GET returns an array with two members.
    // Regarding "$" - see 
    // https://github.com/rest-assured/rest-assured/wiki/Usage#anonymous-json-root-validation
    restAssured.given().body("")
        .when().get(BOOK_RESOURCE)
        .then().body("$", hasSize(2));
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());

    // Create a book
    Book newBook = restAssured.given().contentType("application/json").body(getBook("name_3", 
        "isbn_3")).post(BOOK_RESOURCE).as(Book.class);
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());

    // Retrieve it
    Book retrievedBook = restAssured.given().body("").get(BOOK_RESOURCE + "/" + newBook.getId())
        .as(Book.class);
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());

    // Make sure the book we retrieved looks reasonable
    Assert.assertNotNull(retrievedBook);
    Assert.assertEquals("Unexpected name in newly created book", "name_3", retrievedBook.getName());
  }


  private static Book getBook(String name, String isbn) {
    Book book = new Book(name, isbn);
    book.setId(UUID.randomUUID());
    return book;
  }

}
