/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.example.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import com.jayway.restassured.RestAssured;
import guru.nidi.ramltester.RamlDefinition;
import guru.nidi.ramltester.RamlLoaders;
import guru.nidi.ramltester.junit.RamlMatchers;
import guru.nidi.ramltester.restassured.RestAssuredClient;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.example.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/*
  This class exists in order to illustrate the currently recommended way of performing 
  integration testing. The RestAssured library makes it easy to make Restful calls and validate 
  their responses. For each client-server interaction, an assertion is added to verify that the 
  HTTP requests and responses match those defined within the "api-definition.yaml" RAML file.
*/
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
@SpringBootTest
@Transactional
@Ignore
@SuppressWarnings({"PMD"})
public class BookIntegrationTest {

  private static final String RAML_ASSERT_MESSAGE = "HTTP request/response should match RAML "
      + "definition.";

  private static final String BOOK_RESOURCE = "/api/books";
  private RestAssuredClient restAssured;

  private static final RamlDefinition ramlDefinition =
      RamlLoaders.fromClasspath().load("api-definition-raml.yaml").ignoringXheaders();

  @Value("${auth.server.baseUrl}")
  private String baseUri;

  @Before
  public void setUp() {
    RestAssured.baseURI = baseUri;
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
