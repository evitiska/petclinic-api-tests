package Owner;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.junit.jupiter.api.Test;

public class OwnerTests {

    @Test
    public void getAllOwners() {
        given()
                .when()
                .get("owners/list") // Note: this call is not listed in the API spec.
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("Owner/OwnerListSchema.json"));
    }
}
