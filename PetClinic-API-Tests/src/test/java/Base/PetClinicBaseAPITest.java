package Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public abstract class PetClinicBaseAPITest {

        static {
            String targetHost = System.getenv("TARGET_HOST");
            if (targetHost == null || targetHost.isEmpty()) {
                targetHost = "localhost";
            }

            RestAssured.baseURI = "http://" + targetHost;
            RestAssured.port = 8080;

            System.out.println("🔗 Using baseURI [" + RestAssured.baseURI + ":" + RestAssured.port + "]" );
        }

        static {
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
            TestData.ownerId = firstOwnerId;
            TestData.petId = firstPetId;
        }
}
