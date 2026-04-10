package ru.yandex.practikum;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class ListOrdersTest {
    @Test
    public void checkOrdersListNotEmpty() {
        new OrderClient().getList()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", not(empty()));
    }
}