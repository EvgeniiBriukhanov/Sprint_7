import Order.MethodsOrder;
import Order.OrderInfo;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static constants.Endpoints.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    private int track;
    private final List<String> colour;
    MethodsOrder methodsOrder = new MethodsOrder();

    public CreateOrderParametrizedTest(List<String> colour) {
        this.colour = colour;
    }
    @Before
    public void setUp(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void cleanOrder(){
        methodsOrder.cancelOrder(track);
    }

    @Parameterized.Parameters
    public static Object[][] selectColour(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK","GREY")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @DisplayName("Успешное создание нового заказа")
    @Description("Создает заказ с разными цветами")
    @Test
    public void createOrderTest(){

        OrderInfo orderInfo = new OrderInfo(
                "Vadim",
                "Polejanov",
                "Home",
                "Veselaya",
                "+998900978566",
                "10",
                "11.04.2024",
                "Test",
                colour);
        ValidatableResponse response = methodsOrder.successOrderCreate(orderInfo);
        track = response.extract().path("track");
        response.assertThat().statusCode(201)
                .body("track", is(notNullValue()));
    }
}
