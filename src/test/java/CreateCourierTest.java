import Courier.CourierInfo;
import Courier.Login;
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
import static constants.ErrorText.COURIER_CREATE_INSUFFICIENT_DATA_400;
import static constants.ErrorText.COURIER_DELETE_200;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;;

public class CreateCourierTest {
    private final int random = 1 + (int) (Math.random() * 1000);

    String courierId;

    @Before
    public void setUp() {

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;


//        responseLogin.then().assertThat().statusCode(200);

//        courierId = responseLogin.then().extract().path("id");

    }

    @After
    @Step("Удаление ранее созданного курьера")
    public void deleteCourier() {
        Response responseDelete =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(DELETE_COURIER + courierId);
        responseDelete.then().assertThat().statusCode(200)
        .body("ok", equalTo(COURIER_DELETE_200));
    }

    @Step("Тест успешного создание курьера")
    @Test
    public void successCreateNewCourier() {
        CourierInfo courierInfo = new CourierInfo("Zabuhalov" + random, "1234", "Petrovich" + random);
        Login login = new Login("Zabuhalov" + random, "1234");
        Response responseCreate =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierInfo)
                        .when()
                        .post(POST_COURIER_CREATE);
        responseCreate.then().assertThat().statusCode(201);

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post(POST_COURIER_LOGIN);
        courierId = responseLogin.toString();

 //       courierId = responseLogin.extract().path("id");
    }
    @Step("Тест создание курьера без логина")
    @Test
    public void failedToCreateCourier () {
        CourierInfo courierInfo = new CourierInfo(null, "1234", "Petrovich" + random);
        ValidatableResponse responseCreate =
                given()
                        .header("Content-type", "application/json")
                        .body(courierInfo)
                        .when()
                        .post(POST_COURIER_CREATE).then();
        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }
}