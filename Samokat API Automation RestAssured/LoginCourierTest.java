package ru.yandex.practikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.model.Courier;
import ru.yandex.practikum.model.CourierCredentials;
import ru.yandex.practikum.utils.Utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Utils.getRandomCourier();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        if (courierId == 0) {
            try {
                courierId = courierClient.login(CourierCredentials.from(courier))
                        .extract().path("id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void loginSuccess() {
        courierId = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void loginWithWrongPassword() {
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), "wrongPass");

        courierClient.login(creds)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithNonExistentUser() {
        CourierCredentials creds = new CourierCredentials("nonExistentLogin12345", "1234");

        courierClient.login(creds)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLoginField() {
        CourierCredentials creds = new CourierCredentials(null, courier.getPassword());

        courierClient.login(creds)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}