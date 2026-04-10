package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.OrderPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private OrderPage orderPage;

    // Параметры для теста - два набора данных
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final boolean useTopButton;

    public OrderTest(String name, String surname, String address, String metro,
                     String phone, String date, String rentalPeriod, String color,
                     String comment, boolean useTopButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // Первый набор: верхняя кнопка
                {"Екатерина", "Грачёва", "ул. Победы 5", "Университет",
                        "+7271825654", "10.11.2025", "трое суток", "серая безысходность", "", true},

                // Второй набор: нижняя кнопка
                {"Анна", "Антонова", "ул. Мира 12", "Лубянка",
                        "+79021825688", "11.11.2025", "двое суток", "черный жемчуг", "Осторожно, собака!", false}
        });
    }

    @Test
    public void testOrderScooter() {
        orderPage = new OrderPage(driver);

        // Выбор точки входа - проверяем обе кнопки заказа
        if (useTopButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Заполнение первой страницы заказа
        orderPage.fillFirstPage(name, surname, address, metro, phone);

        // Заполнение второй страницы заказа
        orderPage.fillSecondPage(date, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        // Проверка успешного оформления заказа
        assertTrue("Заказ не был оформлен успешно", orderPage.isOrderSuccess());
    }
}