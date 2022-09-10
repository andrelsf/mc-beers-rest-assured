package br.dev.multicode.automation.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import br.dev.multicode.automation.core.BaseTest;
import br.dev.multicode.automation.core.TestUtils;
import java.util.UUID;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiBeersTest extends BaseTest {

  private static String beerId;


  @Test
  public void t01_shouldPostCreateANewBeer() {
    beerId = given()
        .body(TestUtils.createANewBeer())
      .when()
        .post()
      .then()
        .statusCode(201)
        .header("Location", notNullValue())
        .header("resourceId", notNullValue())
        .extract().header("resourceId");
  }

  @Test
  public void t02_shouldUpdateBeerCreatedById() {
    given()
        .pathParam("beerId", beerId)
        .body(TestUtils.updateABeer())
      .when()
        .put("/{beerId}")
      .then()
        .statusCode(204);
  }

  @Test
  public void t03_shouldGETBeerById() {
    given()
        .pathParam("beerId", beerId)
      .when()
        .get("/{beerId}")
      .then()
        .statusCode(200)
        .body("id", is(beerId));
  }

  @Test
  public void t04_shouldFailGETBeerWithInvalidId() {
    final String invalidBeerId = UUID.randomUUID().toString();
    given()
        .pathParam("beerId", invalidBeerId)
      .when()
        .get("/{beerId}")
      .then()
        .assertThat().statusCode(404)
        .assertThat().body("message", is("Beer not found by ID=".concat(invalidBeerId)));
  }

  @Test
  public void t05_shouldFailPOSTWithoutBody() {
    given()
      .when()
        .post()
      .then()
        .statusCode(400)
        .body("code", is(400))
        .body("message", is("Bad Request"));
  }

  @Test
  public void t06_shouldFailPOSTWithNullObjectAttributes() {
    given()
        .body(TestUtils.createAInvalidBeer())
      .when()
        .post()
      .then()
        .statusCode(400)
        .body("$", hasSize(4))
        .body("message", hasItem("'alcoholContent' não deve ser nulo"))
        .body("message", hasItem("'ingredients' não deve estar em branco"))
        .body("message", hasItem("'name' não deve estar em branco"))
        .body("message", hasItem("'price' não deve ser nulo"));
  }

  @Test
  public void t07_shouldDeleteBeerById() {
    given()
        .pathParam("beerId", beerId)
      .when()
        .delete("/{beerId}")
      .then()
        .assertThat().statusCode(204);
  }
}
