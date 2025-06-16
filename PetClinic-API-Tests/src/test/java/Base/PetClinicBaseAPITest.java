package Base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class PetClinicBaseAPITest {

        @BeforeAll
        public void setup() {
            String targetHost = System.getenv("TARGET_HOST");
            if (targetHost == null || targetHost.isEmpty()) {
                targetHost = "localhost";
            }

            RestAssured.proxy(targetHost, 8080);
        }
}
