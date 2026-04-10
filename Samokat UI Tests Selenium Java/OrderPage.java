package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Первая страница заказа - поля формы
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Вторая страница заказа - дополнительные поля
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    private final String rentalPeriodOption = "//div[contains(@class, 'Dropdown-option') and text()='%s']";
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    // Модальное окно подтверждения заказа
    private final By confirmationModal = By.xpath(".//div[contains(@class, 'Order_Modal') and .//button[text()='Да']]");
    private final By yesButton = By.xpath(".//div[contains(@class, 'Order_Modal')]//button[text()='Да']");
    private final By successMessage = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");

    // Станции метро - шаблон локатора
    private final String metroStationOption = "//li[contains(@class, 'select-search__row')]//button[contains(., '%s')]";

    // Цвета самоката
    private final By greyColorCheckbox = By.id("grey");
    private final By blackColorCheckbox = By.id("black");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillFirstPage(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(surnameField)).sendKeys(surname);
        wait.until(ExpectedConditions.elementToBeClickable(addressField)).sendKeys(address);
        wait.until(ExpectedConditions.elementToBeClickable(phoneField)).sendKeys(phone);

        // Выбор станции метро с ожиданием появления списка
        WebElement metroInput = wait.until(ExpectedConditions.elementToBeClickable(metroField));
        metroInput.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'select-search__select')]")));

        // Клик по выбранной станции метро
        By metroOption = By.xpath(String.format(metroStationOption, metro));
        WebElement metroElement = wait.until(ExpectedConditions.elementToBeClickable(metroOption));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", metroElement);
        metroElement.click();

        // Переход к следующей странице
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", nextBtn);
        nextBtn.click();
    }

    public void fillSecondPage(String date, String rentalPeriod, String color, String comment) {
        // Заполнение даты с очисткой поля
        setDateCorrectly(date);

        // Выбор периода аренды
        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        periodField.click();

        By periodOption = By.xpath(String.format(rentalPeriodOption, rentalPeriod));
        WebElement periodElement = wait.until(ExpectedConditions.elementToBeClickable(periodOption));
        periodElement.click();

        // Выбор цвета самоката
        selectColor(color);

        // Заполнение комментария если указан
        if (comment != null && !comment.trim().isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(commentField)).sendKeys(comment);
        }

        // Нажатие кнопки заказа
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", orderBtn);
        orderBtn.click();
    }

    private void setDateCorrectly(String date) {
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(dateField));

        // Активируем поле и очищаем его
        dateElement.click();
        dateElement.sendKeys(Keys.CONTROL + "a");
        dateElement.sendKeys(Keys.DELETE);

        // Ждем очистки поля
        wait.until(ExpectedConditions.attributeToBe(dateField, "value", ""));

        // Ввод даты и подтверждение
        dateElement.sendKeys(date);
        dateElement.sendKeys(Keys.ENTER);

        // Клик для применения даты
        driver.findElement(By.tagName("body")).click();

        // Ждем применения даты
        wait.until(ExpectedConditions.attributeToBe(dateField, "value", date));
    }

    private void selectColor(String color) {
        if (color.equals("серая безысходность")) {
            WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", colorElement);
            colorElement.click();
        } else if (color.equals("черный жемчуг")) {
            WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", colorElement);
            colorElement.click();
        }
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", yesBtn);
        yesBtn.click();
    }

    public boolean isOrderSuccess() {
        try {
            WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return successElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}