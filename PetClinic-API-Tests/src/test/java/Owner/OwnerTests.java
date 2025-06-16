package Owner;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OwnerTests {

    int firstOwnerId;

    @BeforeAll
    public void setup() {
        Response response =
                given()
                        .when()
                        .get("owners/list") // Note: this call is not listed in the API spec.
                        .then()
                        .extract()
                        .response();

        int firstOwnerId = response.path("[0].id");

        assertNotNull(firstOwnerId);
        this.firstOwnerId = firstOwnerId;
    }

    @Test
    public void getAllOwners() {
        given()
                .when()
                .get("owners/list") // Note: this call is not listed in the API spec.
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Owner/OwnerListSchema.json"));
    }

    @Test
    public void createNewOwner_withValidBody() {
        Owner requestBodyOwner = new Owner("Bart", "Simpson", "Sesame Street", "Amsterdam", "06123456789");

        given()
                .contentType("application/json")
                .body(requestBodyOwner)
                .when()
                .post("owners")
                .then()
                .statusCode(201);
    }

    @Test
    public void createNewOwner_withInvalidBody() {
        Owner emptyValuesBody = new Owner("", "", "", "", "");

        given()
                .contentType("application/json")
                .body(emptyValuesBody)
                .when()
                .post("owners")
                .then()
                .statusCode(400);
    }
    @Test
    public void createNewOwner_withoutBody() {
        given()
                .contentType("application/json")
                .when()
                .post("owners")
                .then()
                .statusCode(400);
    }

    @Test
    public void getOwner_byValidId() {
        given()
                .when()
                .pathParam("ownerId", firstOwnerId)
                .get("owners/{ownerId}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Owner/OwnerSchema.json"));

    }

    @Test
    public void getOwner_byInvalidId() {
        given()
                .when()
                .pathParam("ownerId", -firstOwnerId)
                .get("owners/{ownerId}")
                .then()
                .statusCode(500); // Note: The API spec says this should be HTTP 500 instead.
    }

    @Test
    public void updateOwner_withValidBody() {
        Owner body = new Owner("Elmo", "Elmorino", "Sesame Street 50", "Rotterdam", "06123456789");

        Owner returnedOwner = given()
                .contentType("application/json")
                .body(body)
                .when()
                .pathParam("ownerId", firstOwnerId)
                .put("owners/{ownerId}")
                .then()
                .statusCode(200)
                .extract()
                .as(Owner.class);

        assertEquals(body, returnedOwner);
    }

    @Test
    public void updateOwner_withInvalidBody() {
        Owner body = new Owner("", "", "", "", "");

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .pathParam("ownerId", firstOwnerId)
                .put("owners/{ownerId}")
                .then()
                .statusCode(400);
    }

    @Test
    @Disabled("Endpoint is returning 405 for a valid id")
    public void deleteOwner_byValidId() {
        given()
                .when()
                .pathParam("ownerId", firstOwnerId)
                .delete("owners/{ownerId}")
                .then()
                .statusCode(200);
    }
}
