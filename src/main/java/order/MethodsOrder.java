package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsOrder {

    @Step("Создание заказа")
    public ValidatableResponse successOrderCreate(OrderInfo orderInfo) {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .when()
                .post(POST_ORDER_CREATE)
                .then();
        return responseOrder;
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
        return responseOrder;
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .when()
                .get(GET_ORDERS_LIST)
                .then();
        return responseOrder;
    }
}
