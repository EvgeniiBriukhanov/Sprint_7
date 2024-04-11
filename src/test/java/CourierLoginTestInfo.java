import courier.CourierInfo;
import courier.LoginInfo;
import courier.MethodsCourier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Endpoints.BASE_URL;
import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierLoginTestInfo {
    private int courierId;
    private final int random = 1 + (int) (Math.random() * 10000);
    protected CourierInfo courierInfo = new CourierInfo("Zabuhalov" + random, "1234", "Petrovich" + random);
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
            System.out.println(MESSAGE_DELETE_COURIER);
        }
    }

    @DisplayName("Авторизация курьера с валидными данными")
    @Description("Успешная авторизация курьера")
    @Test
    public void successLoginCourierTest() {
        ValidatableResponse responseCreate = methodsCourier.createCourier(courierInfo);
        responseCreate.assertThat().statusCode(201).and().body("ok", equalTo(COURIER_CREATE_200));

        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        ValidatableResponse courierLogin = methodsCourier.courierAuthorization(loginInfoCourier);
        courierId = courierLogin.extract().path("id");
        courierLogin.assertThat().statusCode(200)
                .body("ok", equalTo(LOGIN_SUCCESSFUL_200));
    }

    @DisplayName("Авторизация курьера с несуществующими данными")
    @Description("Проверка получение ошибки при авторизации с несуществующими данными")
    @Test
    public void failedLoginCourierWithUnknownDataTest() {
        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        loginInfoCourier.setLogin("Unknown");
        loginInfoCourier.setPassword("Unknown");
        ValidatableResponse courierLogin = methodsCourier.courierAuthorization(loginInfoCourier);
        courierLogin.assertThat().statusCode(404)
                .body("message", equalTo(LOGIN_NON_EXISTENT_PASSWORD_OR_LOGIN_404));
    }

    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка получение ошибки при авторизации без логина")
    @Test
    public void failedLoginCourierWithEmptyLoginTest() {
        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        loginInfoCourier.setLogin(null);
        ValidatableResponse courierLogin = methodsCourier.courierAuthorization(loginInfoCourier);
        courierLogin.assertThat().statusCode(400)
                .body("message", equalTo(LOGIN_CREATE_INSUFFICIENT_DATA_400));
    }

    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка получение ошибки при авторизации без пароля")
    @Test
    public void failedLoginCourierWithEmptyPasswordTest() {
        LoginInfo loginInfoCourier = LoginInfo.from(courierInfo);
        loginInfoCourier.setPassword("");
        ValidatableResponse courierLogin = methodsCourier.courierAuthorization(loginInfoCourier);
        courierLogin.assertThat().statusCode(400)
                .body("message", equalTo(LOGIN_CREATE_INSUFFICIENT_DATA_400));
    }
}