package ru.yandex.practikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practikum.model.Order;
import ru.yandex.practikum.utils.Utils;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> colors;

    public CreateOrderTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Colors: {0}")
    public static Object[][] getColorsData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        };
    }

    @Test
    public void createOrderSuccessfully() {
        OrderClient orderClient = new OrderClient();
        Order order = Utils.getRandomOrder(colors);

        orderClient.create(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}