import Courier.CourierInfo;
import Courier.Login;
import Courier.MethodsCourier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Endpoints.*;
import static constants.ErrorText.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;;

public class CreateCourierTest {

    private final int random = 1 + (int) (Math.random() * 1000);

    CourierInfo courierInfo = new CourierInfo("Zabuhalov" + random, "1234", "Petrovich" + random);

    MethodsCourier methodsCourier = new MethodsCourier();
    private int courierId;

    @Before
    public void setUp() {

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    @After
    @Step("Удаление ранее созданного курьера")
    public void deleteCourier() {
        ValidatableResponse responseDelete = methodsCourier.courierDelete(courierId);
        responseDelete.assertThat().statusCode(200)
                .body("ok", equalTo(COURIER_DELETE_200));
    }

    @Step("Тест успешного создание курьера")
    @Test
    public void successCreateNewCourier() {
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        responseCreate.assertThat().statusCode(201).and().body("ok", equalTo(COURIER_CREATE_200));

        courierId = methodsCourier.courierAuthorization(courierInfo).extract().path("id");
    }

    @Step("Тест создание курьера без логина")
    @Test
    public void failedToCreateCourier() {
        CourierInfo courierInfo = new CourierInfo(null, "1234", "Petrovich" + random);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);

        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }
}