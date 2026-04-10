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
import praktikum.pom.*;
import praktikum.util.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private User user;
    private String accessToken;
    private UserClient userClient;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();

        Response response = userClient.create(user);
        if (response.statusCode() == SC_OK) {
            accessToken = response.path("accessToken");
        }

        driver.get(AppConfig.BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Успешный вход через главную страницу")
    public void loginFromMainPageButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Успешный вход через хедер")
    public void loginFromPersonalCabinet() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalCabinet();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Переход на логин со страницы регистрации")
    public void loginFromRegistrationPage() {
        driver.get(AppConfig.BASE_URL + "/register");
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(new MainPage(driver).isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Переход на логин со страницы восстановления пароля")
    public void loginFromForgotPasswordPage() {
        driver.get(AppConfig.BASE_URL + "/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(new MainPage(driver).isOrderButtonVisible());
    }
}