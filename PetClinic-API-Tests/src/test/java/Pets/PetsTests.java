package Pets;
import Base.PetClinicBaseAPITest;
import Base.TestData;
import DTO.AddPetPayload;
import DTO.EditPetPayload;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PetsTests extends PetClinicBaseAPITest {
    private static int PET_TYPE_CAT = 1;
    private static int PET_TYPE_INVALID = 999;
    @Test
    public void getPetByValidId() {
        given()
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
                .pathParam("petId", TestData.getPetId())
                .get("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Pet/PetSchema.json"));
    }

    @Test
    @Tag("spec-deviation")
    public void getPetByInvalidId() {
        given()
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
                .pathParam("petId", -TestData.getPetId())
                .get("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/ErrorSchema.json"));
    }

    @Test
    @Tag("spec-deviation")
    public void addPetToValidOwner() {
        AddPetPayload payload = new AddPetPayload("Test-Pet", PET_TYPE_CAT, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", TestData.getOwnerId())
                .post("owners/{ownerId}/pets")
                .then()
                .statusCode(201); // This returns 204 in reality
    }

    @Test
    @Tag("spec-deviation")
    public void addPetToValidOwnerWithInvalidType() {
        AddPetPayload payload = new AddPetPayload("Test-Pet", PET_TYPE_INVALID, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", TestData.getOwnerId())
                .post("owners/{ownerId}/pets")
                .then()
                .statusCode(400);
    }

    @Test
    public void editPetInformation() {
        EditPetPayload payload = new EditPetPayload(TestData.getPetId(), "Test-Pet", 2, "2025-06-02");
        given()
                .when()
                .contentType("application/json")
                .body(payload)
                .pathParam("ownerId", TestData.getOwnerId())
                .pathParam("petId", TestData.getPetId())
                .put("owners/{ownerId}/pets/{petId}")
                .then()
                .statusCode(204);
    }
}
