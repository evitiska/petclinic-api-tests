package Pets;

import Base.PetClinicBaseAPITest;
import DTO.AddPetVisitPayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VisitsTests extends PetClinicBaseAPITest {
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

        assertNotNull(firstOwnerId, "Cannot extract existing owner ID");
        assertNotNull(firstPetId, "Cannot extract existing pet ID");
        this.firstOwnerId = firstOwnerId;
        this.firstPetId = firstPetId;
    }

    @Test
    public void getPetVisits() {
        given()
                .when()
                .pathParam("ownerId", firstOwnerId)
                .pathParam("petId", firstPetId)
                .get("owners/{ownerId}/pets/{petId}/visits")
                .then()
                .statusCode(200);
    }

    @Test
    public void addPetVisit() {
        AddPetVisitPayload payload = new AddPetVisitPayload("2025-06-17", "A regular checkup");
        given()
                .when()
                .contentType("application/json")
                .pathParam("ownerId", firstOwnerId)
                .pathParam("petId", firstPetId)
                .body(payload)
                .post("owners/{ownerId}/pets/{petId}/visits")
                .then()
                .statusCode(201); // Actually returns 204 here.
    }
}
