import courier.CourierInfo;
import courier.LoginInfo;
import courier.MethodsCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;

import org.junit.Test;

import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {

    private final int random = 1 + (int) (Math.random() * 10000);

    protected CourierInfo courierInfo = new CourierInfo("Zabuhalov" + random, "1234", "Petrovich" + random);

    protected MethodsCourier methodsCourier = new MethodsCourier();
    private int courierId;
    @DisplayName("Создание курьера с валидными данными")
    @Description("Успешного создание курьера")
    @Test
    public void successCreateNewCourierTest() {
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        responseCreate.assertThat().statusCode(201).and().body("ok", equalTo(COURIER_CREATE_200));
        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        courierId = methodsCourier.courierAuthorization(loginInfoCourier).extract().path("id");
    }

    @DisplayName("Создание курьера без логина")
    @Description("Проверка получение ошибки при создании курьера без логина")
    @Test
    public void failedCreatingCourierWithoutLoginTest() {
        courierInfo.setLogin(null);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);

        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }

    @DisplayName("Создание курьера без пароля")
    @Description("Проверка получение ошибки при создании курьера без пароля")
    @Test
    public void failedCreatingCourierWithoutPasswordTest() {
        courierInfo.setPassword(null);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);

        responseCreate.assertThat()
                .statusCode(400)
                .body("message", equalTo(COURIER_CREATE_INSUFFICIENT_DATA_400));
    }

    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка получение ошибки при создании двух одинаковых курьеров")
    @Test
    public void failedCreatingTwoIdenticalCouriersTest() {
        methodsCourier.createCourier(courierInfo);
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        courierId = methodsCourier.courierAuthorization(loginInfoCourier).extract().path("id");
        responseCreate.assertThat().statusCode(409).and().body("message", equalTo(COURIER_CREATE_DOUBLE_409));
    }
}