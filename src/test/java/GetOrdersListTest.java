import Order.MethodsOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static constants.Endpoints.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {
    MethodsOrder methodsOrder = new MethodsOrder();

    @Before
    public void setUp(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    @DisplayName("Проверка получения списка заказов")
    @Description("Тест получения списка заказов")
    @Test
    public void GetListOrdersTest(){
        ValidatableResponse response = methodsOrder.getOrdersList();
        response.assertThat().statusCode(200)
                .body("order", is(notNullValue()));
    }
}
