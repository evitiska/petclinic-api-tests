package Pets;

import Base.PetClinicBaseAPITest;
import Base.TestData;
import DTO.AddPetVisitPayload;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class VisitsTests extends PetClinicBaseAPITest {

    @Test
    @Tag("spec-deviation")
    public void getPetVisits() {
        given()
                .when()
                .pathParam("ownerId", TestData.getOwnerId())
                .pathParam("petId", TestData.getPetId())
                .get("owners/{ownerId}/pets/{petId}/visits")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Pet/PetVisitsSchema.json"));
    }

    @Test
    @Tag("spec-deviation")
    public void addPetVisit() {
        AddPetVisitPayload payload = new AddPetVisitPayload("2025-06-17", "A regular checkup");
        given()
                .when()
                .contentType("application/json")
                .pathParam("ownerId", TestData.getOwnerId())
                .pathParam("petId", TestData.getPetId())
                .body(payload)
                .post("owners/{ownerId}/pets/{petId}/visits")
                .then()
                .statusCode(201) // Actually returns 204 here.
                .body(matchesJsonSchemaInClasspath("schemas/Pet/PetVisitSchema.json"));
    }
}
