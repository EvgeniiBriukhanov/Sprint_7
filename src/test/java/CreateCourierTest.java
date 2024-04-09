import Courier.CourierInfo;
import Courier.Login;
import Courier.MethodsCourier;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Endpoints.*;
import static constants.ErrorText.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    private final int random = 1 + (int) (Math.random() * 10000);

    protected CourierInfo courierInfo = new CourierInfo("Zabuhalov" + random, "1234", "Petrovich" + random);

    protected Login login;

    protected MethodsCourier methodsCourier = new MethodsCourier();
    private int courierId;

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
            System.out.println(MESSAGE_DELETE_COURIER);
        }
    }

    @Step("Тест успешного создание курьера")
    @Test
    public void successCreateNewCourierTest() {
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        responseCreate.assertThat().statusCode(201).and().body("ok", equalTo(COURIER_CREATE_200));
        Login loginCourier = Login.from(courierInfo);
        courierId = methodsCourier.courierAuthorization(loginCourier).extract().path("id");
    }

    @Step("Тест создание курьера без логина")
    @Test
    public void failedCreatingCourierWithoutLoginTest() {
        courierInfo.setLogin(null);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);

        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }

    @Step("Тест создание курьера без пароля")
    @Test
    public void failedCreatingCourierWithoutPasswordTest() {
        courierInfo.setPassword(null);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);

        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }

    @Step("Тест создание двух одинаковых курьеров")
    @Test
    public void failedCreatingTwoIdenticalCouriersTest() {
        methodsCourier.createCourier(courierInfo);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        Login loginCourier = Login.from(courierInfo);
        courierId = methodsCourier.courierAuthorization(loginCourier).extract().path("id");
        responseCreate.assertThat().statusCode(409).and().body("message", equalTo(COURIER_CREATE_DOUBLE_409));
    }
}