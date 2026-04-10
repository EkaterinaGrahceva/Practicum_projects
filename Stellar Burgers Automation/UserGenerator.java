package praktikum.util;

import com.github.javafaker.Faker;
import praktikum.model.User;

public class UserGenerator {
    private static final Faker faker = new Faker();

    public static User getRandomUser() {
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(6, 12),
                faker.name().username()
        );
    }
}