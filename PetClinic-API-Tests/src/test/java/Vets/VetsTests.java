package Vets;

import Base.PetClinicBaseAPITest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VetsTests extends PetClinicBaseAPITest {
    @Test
    public void getAllVets() {
        given()
                .when()
                .get("vets")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Vets/VetsListSchema.json"));
    }

    @Test
    public void addNewVet() {
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                                "firstName": "Bart",
                                "lastName": "Simpson",
                                "specialties": [ { "name": "Trouble"} ]
                        }
                        """)
                .post("vets")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/Vets/VetSchema.json"));
    }
}
