package ru.yandex.practikum.utils;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practikum.model.Courier;
import ru.yandex.practikum.model.Order;

import java.util.List;

public class Utils {

    public static Courier getRandomCourier() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = "12345";

        String firstName = RandomStringUtils.random(10, "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");

        return new Courier(login, password, firstName);
    }

    public static Order getRandomOrder(List<String> colors) {
        return new Order(
                "Наруто",
                "Узумаки",
                "Коноха, кв. 142",
                "4",
                "+7 800 355 35 35",
                5,
                "2024-06-06",
                "Саске, возвращайся в Коноху",
                colors
        );
    }
}