package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.client.UserClient;
import praktikum.config.AppConfig;
import praktikum.model.User;
import praktikum.pom.LoginPage;
import praktikum.pom.RegisterPage;
import praktikum.util.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        driver.get(AppConfig.BASE_URL + "/register");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        Response loginResponse = userClient.login(user);
        if (loginResponse.statusCode() == SC_OK) {
            String accessToken = loginResponse.path("accessToken");
            if (accessToken != null) {
                userClient.delete(accessToken);
            }
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Регистрация пользователя с валидными данными")
    public void successfulRegistration() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoad();
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Ошибка регистрации: некорректный пароль")
    @Description("Попытка регистрации с паролем менее 6 символов")
    public void registrationWithShortPassword() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), "123");

        assertTrue("Ошибка 'Некорректный пароль' должна отображаться",
                registerPage.isPasswordErrorDisplayed());
    }
}