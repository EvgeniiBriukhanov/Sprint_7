import courier.MethodsCourier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;

import java.util.logging.Logger;

import static constants.Endpoints.BASE_URL;
import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class BasaTest {

    private int courierId;
    protected MethodsCourier methodsCourier = new MethodsCourier();

    @Before
    @Step("Базовые тестовых данные")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    @After
    @Step("Удаление ранее созданного курьера")
    public void deleteData() {
        if (courierId == 0) {
            System.out.println(COURIER_EMPTY);
        } else {
            ValidatableResponse responseDelete = methodsCourier.courierDelete(courierId);
            responseDelete.assertThat().statusCode(200)
                    .body("ok", equalTo(COURIER_DELETE_200));
            Logger.getLogger(MESSAGE_DELETE_COURIER);
        }
    }
}
