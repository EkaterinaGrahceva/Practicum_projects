package ru.yandex.practikum;

import org.junit.Test;
import ru.yandex.practikum.model.Order;
import ru.yandex.practikum.utils.Utils;

import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {

    @Test
    public void getOrderSuccessfully() {
        OrderClient client = new OrderClient();
        Order order = Utils.getRandomOrder(List.of("GREY"));

        int track = client.create(order).extract().path("track");

        client.getOrderByTrack(track)
                .statusCode(200)
                .body("order", notNullValue());
    }

    @Test
    public void getOrderByNonExistentTrack() {
        new OrderClient().getOrderByTrack(0)
                .statusCode(404)
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    public void getOrderWithoutTrack() {
        new OrderClient().getOrderWithoutTrack()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }
}