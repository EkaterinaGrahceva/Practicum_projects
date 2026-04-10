package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.config.AppConfig;
import praktikum.pom.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.get(AppConfig.BASE_URL);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Клик на таб Соусы и проверка активности")
    public void checkSaucesTabNavigation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();
        assertTrue("Вкладка Соусы должна стать активной", mainPage.isSaucesTabActive());
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Клик на таб Начинки и проверка активности")
    public void checkFillingsTabNavigation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsTab();
        assertTrue("Вкладка Начинки должна стать активной", mainPage.isFillingsTabActive());
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Переход на Соусы, затем возврат на Булки")
    public void checkBunsTabNavigation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isSaucesTabActive());

        mainPage.clickBunsTab();
        assertTrue("Вкладка Булки должна стать активной", mainPage.isBunsTabActive());
    }
}