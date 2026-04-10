package ru.yandex.practikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practikum.model.Courier;
import ru.yandex.practikum.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создание курьера с логином: {courier.login}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .post(COURIER_PATH)
                .then();
    }

    @Step("Логин курьера в системе (login: {credentials.login})")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .post(COURIER_PATH + "/login")
                .then();
    }

    @Step("Удаление курьера по ID: {courierId}")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .delete(COURIER_PATH + "/" + courierId)
                .then();
    }

    @Step("Попытка удаления курьера без передачи ID")
    public ValidatableResponse deleteWithoutId() {
        return given()
                .spec(getBaseSpec())
                .delete(COURIER_PATH + "/")
                .then();
    }
}