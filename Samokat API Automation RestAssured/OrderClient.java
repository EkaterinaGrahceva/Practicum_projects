package ru.yandex.practikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practikum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getList() {
        return given()
                .spec(getBaseSpec())
                .get(ORDERS_PATH)
                .then();
    }

    @Step("Получение заказа по номеру трека: {track}")
    public ValidatableResponse getOrderByTrack(int track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .get(ORDERS_PATH + "/track")
                .then();
    }

    @Step("Получение заказа без номера трека")
    public ValidatableResponse getOrderWithoutTrack() {
        return given()
                .spec(getBaseSpec())
                .get(ORDERS_PATH + "/track")
                .then();
    }

    @Step("Принятие заказа (ID заказа: {orderId}, ID курьера: {courierId})")
    public ValidatableResponse acceptOrder(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .put(ORDERS_PATH + "/accept/" + orderId)
                .then();
    }
}