package order;

import courier.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsOrder extends BaseClient {

    @Step("Создание заказа")
    public ValidatableResponse successOrderCreate(OrderInfo orderInfo) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .post(POST_ORDER_CREATE)
                .then();

    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(GET_ORDERS_LIST)
                .then();
    }
}
