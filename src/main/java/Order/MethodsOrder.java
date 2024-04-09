package Order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsOrder {

    public ValidatableResponse successOrderCreate(OrderInfo orderInfo) {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .when()
                .post(POST_ORDER_CREATE)
                .then();
        return responseOrder;
    }


    public ValidatableResponse cancelOrder(int track) {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
        return responseOrder;
    }


    public ValidatableResponse getOrdersList() {
        ValidatableResponse responseOrder = given()
                .header("Content-type", "application/json")
                .when()
                .get(GET_ORDERS_LIST)
                .then();
        return responseOrder;
    }
}
