package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsCourier extends BaseClient {

    @Step("регистрация нового курьера")
    public ValidatableResponse createCourier(CourierInfo courierInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(courierInfo)
                .when()
                .post(POST_COURIER_CREATE).then();
    }

    @Step("удаление курьера")
    public ValidatableResponse courierDelete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_COURIER + courierId).then();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierAuthorization(LoginInfo loginInfo) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginInfo)
                .when()
                .post(POST_COURIER_LOGIN).then();
    }
}
