package praktikum.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalCabinetButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик на кнопку Войти в аккаунт")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик на Личный кабинет")
    public void clickPersonalCabinet() {
        driver.findElement(personalCabinetButton).click();
    }

    @Step("Клик на вкладку Булки")
    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    @Step("Клик на вкладку Соусы")
    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    @Step("Клик на вкладку Начинки")
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    private boolean isTabActive(By tabLocator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.attributeContains(tabLocator, "class", "current"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка активности вкладки Булки")
    public boolean isBunsTabActive() {
        return isTabActive(bunsTab);
    }

    @Step("Проверка активности вкладки Соусы")
    public boolean isSaucesTabActive() {
        return isTabActive(saucesTab);
    }

    @Step("Проверка активности вкладки Начинки")
    public boolean isFillingsTabActive() {
        return isTabActive(fillingsTab);
    }

    @Step("Проверка видимости кнопки Оформить заказ")
    public boolean isOrderButtonVisible() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
            return driver.findElement(createOrderButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}