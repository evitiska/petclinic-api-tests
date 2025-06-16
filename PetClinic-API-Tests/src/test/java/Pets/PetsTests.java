package Pets;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetsTests {
    private static int PET_TYPE_CAT = 1;
    private static int PET_TYPE_INVALID = 999;
    int firstOwnerId;
    int firstPetId;

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
        int firstPetId = response.path("[0].pets[0].id");

        assertNotNull(firstOwnerId);
        assertNotNull(firstPetId);
        this.firstOwnerId = firstOwnerId;
        this.firstPetId = firstPetId;
    }

    @Test
    public void getPetByValidId() {
        given()
                .when()
                .pathParam("ownerId", firstOwnerId)
                .pathParam("petId", firstPetId)
                .get("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Pet/PetSchema.json"));
    }

    @Test
    public void getPetByInvalidId() {
        given()
                .when()
                .pathParam("ownerId", firstOwnerId)
                .pathParam("petId", 182318237)
                .get("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(500); // In reality this should probably return a 404.
    }

    @Test
    public void addPetToValidOwner() throws JsonProcessingException {
        AddPetPayload payload = new AddPetPayload("Test-Pet", PET_TYPE_CAT, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", firstOwnerId)
                .post("owners/{ownerId}/pets")
                .then()
                .statusCode(204); // According to the spec, this should return 201 and the newly created pet.
    }

    @Test
    public void addPetToValidOwnerWithInvalidType() throws JsonProcessingException {
        AddPetPayload payload = new AddPetPayload("Test-Pet", PET_TYPE_INVALID, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", firstOwnerId)
                .post("owners/{ownerId}/pets")
                .then()
                .statusCode(500); // This should probably return 400
    }

    @Test
    public void editPetInformation() throws JsonProcessingException {
        EditPetPayload payload = new EditPetPayload(firstPetId, "Test-Pet", 2, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", firstOwnerId)
                .pathParam("petId", firstPetId)
                .put("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(204); // According to the spec, this should return 201 and the newly created pet.
    }
}
