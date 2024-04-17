package courier;

import io.restassured.specification.RequestSpecification;

import static constants.Endpoints.BASE_URL;
import static io.restassured.RestAssured.given;

public class BaseClient {
    public RequestSpecification requestSpecification(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL);
    }
}
