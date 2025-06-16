package Base;

import io.restassured.RestAssured;

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
}
