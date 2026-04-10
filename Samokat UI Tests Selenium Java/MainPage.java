package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Константа для URL
    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    // Главное окно - кнопка принятия куки
    private final By cookieButton = By.id("rcc-confirm-button");

    // Верхняя кнопка заказа
    private final By topOrderButton = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Button__ra12g')]");

    // Нижняя кнопка заказа
    private final By bottomOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    // Блок вопросов - шаблоны локаторов
    private final String questionLocator = "accordion__heading-%d";
    private final String answerLocator = "accordion__panel-%d";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.urlContains("qa-scooter"));
    }

    public void acceptCookies() {
        try {
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBtn.click();
        } catch (Exception e) {
            // Если кнопки куки нет, просто продолжаем
        }
    }

    public void clickTopOrderButton() {
        WebElement topBtn = wait.until(ExpectedConditions.elementToBeClickable(topOrderButton));
        scrollAndClick(topBtn);
    }

    public void clickBottomOrderButton() {
        // Листаем вниз страницы
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Ждем появления нижней кнопки заказа
        WebElement bottomBtn = wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton));

        // Дополнительный скролл к кнопке
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", bottomBtn);

        // Ждем, пока кнопка станет полностью видимой и кликабельной
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(bottomBtn),
                ExpectedConditions.elementToBeClickable(bottomBtn)
        ));

        // Клик через JavaScript
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", bottomBtn);
    }

    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);

        // Ждем, пока элемент станет кликабельным после скролла
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void clickQuestion(int questionIndex) {
        By question = By.id(String.format(questionLocator, questionIndex));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(question));
        scrollAndClick(element);
    }

    public String getAnswerText(int questionIndex) {
        By answer = By.id(String.format(answerLocator, questionIndex));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(answer));
        return answerElement.getText();
    }
}