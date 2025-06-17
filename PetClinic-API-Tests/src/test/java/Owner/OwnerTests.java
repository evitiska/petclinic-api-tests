package Owner;
import Base.PetClinicBaseAPITest;
import Base.TestData;
import DTO.Owner;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class OwnerTests extends PetClinicBaseAPITest {

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
    @Tag("spec-deviation")
    public void createNewOwner_withValidBody() {
        Owner requestBodyOwner = new Owner("Bart", "Simpson", "Sesame Street", "Amsterdam", "06123456789");

        given()
                .contentType("application/json")
                .body(requestBodyOwner)
                .when()
                .post("owners")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/Owner/OwnerSchema.json"));
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
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/ErrorSchema.json"));
    }
    @Test
    public void createNewOwner_withoutBody() {
        given()
                .contentType("application/json")
                .when()
                .post("owners")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/ErrorSchema.json"));
    }

    @Test
    public void getOwner_byValidId() {
        given()
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
                .get("owners/{ownerId}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Owner/OwnerSchema.json"));

    }

    @Test
    @Tag("spec-deviation")
    public void getOwner_byInvalidId() {
        given()
                .when()
                .pathParam("ownerId", -TestData.getOwnerId())
                .get("owners/{ownerId}")
                .then()
                .statusCode(400);
    }

    @Test
    public void updateOwner_withValidBody() {
        Owner body = new Owner("Elmo", "Elmorino", "Sesame Street 50", "Rotterdam", "06123456789");

        Owner returnedOwner = given()
                .contentType("application/json")
                .body(body)
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
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
                .pathParam("ownerId", TestData.getOwnerId())
                .put("owners/{ownerId}")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/ErrorSchema.json"));
    }

    @Test
    @Tag("spec-deviation")
    public void deleteOwner_byValidId() {
        given()
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
                .delete("owners/{ownerId}")
                .then()
                .statusCode(200);
    }
}
