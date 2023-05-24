import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    Order order;
    OrderClient orderClient = new OrderClient();
    int track;

    public CreateOrderTest(Order order) {

        this.order = order;
    }

    @Before
    public void setUp(){

        orderClient.setUp();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new Order("Вася","Иванов","Новая, д.9, кв.17","Белорусская","+79119119191",2,"2023-05-30","комментарий",new String[]{"BLACK"})},
                {new Order("Ченинг","Татум","Дыбенко, b.1, a.11","Белорусская","89999999999",2,"2023-05-25","comment",new String[]{"GREY"})},
                {new Order("Чак","Норррис","г. Москва, ул. Центральный проезд  Ленина, д. 049","ВДНХ","89111111111",3,"2023-05-27","comment",new String[]{"BLACK","GREY"})},
                {new Order("Иван","Таранов","Красная площадь","Зорге","89111231213",4,"2023-05-29","без комментариев",new String[]{})}
        };
    }

    @Test
    @DisplayName("Check create a new order")
    @Description("Create some orders with positive params")
    public void checkCreateOrder(){
        ValidatableResponse response = orderClient.createOrderRequest(order);
        response
                .statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}

