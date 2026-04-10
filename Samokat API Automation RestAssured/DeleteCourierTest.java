package ru.yandex.practikum;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.model.Courier;
import ru.yandex.practikum.model.CourierCredentials;
import ru.yandex.practikum.utils.Utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class DeleteCourierTest {
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void deleteCourierAndVerifyAbsence() {
        Courier courier = Utils.getRandomCourier();
        courierClient.create(courier);

        int id = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        courierClient.delete(id)
                .statusCode(200)
                .body("ok", is(true));

        courierClient.login(CourierCredentials.from(courier))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void deleteNonExistentCourier() {
        courierClient.delete(999999999)
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id нет."));
    }

    @Test
    public void deleteWithoutId() {
        courierClient.deleteWithoutId()
                .statusCode(404);
    }
}