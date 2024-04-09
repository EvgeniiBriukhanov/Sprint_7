package Courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsCourier {

    @Step("регистрация нового курьера")
    public ValidatableResponse createCourier(CourierInfo courierInfo) {
        Response responseCreate = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_CREATE);
        return responseCreate.then();
    }

    @Step("удаление курьера")
    public ValidatableResponse courierDelete(int courierId) {
        Response responseDelete = given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(DELETE_COURIER + courierId);
        return responseDelete.then();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierAuthorization(Login login) {
        Response responseAuthorization = given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post(POST_COURIER_LOGIN);
        return responseAuthorization.then();

    }
}
