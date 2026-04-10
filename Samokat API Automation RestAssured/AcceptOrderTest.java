package ru.yandex.practikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.model.Courier;
import ru.yandex.practikum.model.CourierCredentials;
import ru.yandex.practikum.model.Order;
import ru.yandex.practikum.utils.Utils;

import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AcceptOrderTest {
    private CourierClient courierClient;
    private OrderClient orderClient;
    private int courierId;
    private int orderId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();

        Courier courier = Utils.getRandomCourier();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        Order order = Utils.getRandomOrder(List.of("BLACK"));
        int track = orderClient.create(order).extract().path("track");
        orderId = orderClient.getOrderByTrack(track).extract().path("order.id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void acceptOrderSuccess() {
        orderClient.acceptOrder(orderId, courierId)
                .statusCode(200)
                .body("ok", is(true));
    }

    @Test
    public void acceptOrderWithoutCourierId() {
        orderClient.acceptOrder(orderId, 0)
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    public void acceptOrderWithWrongOrderId() {
        orderClient.acceptOrder(0, courierId)
                .statusCode(404)
                .body("message", equalTo("Заказа с таким id не существует"));
    }
}