package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {

    private final int questionIndex;
    private final String expectedAnswer;

    public FAQTest(int questionIndex, String expectedAnswer) {
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат."},
                {2, "Допустим, вы оформляете заказ на 8 мая."},
                {3, "Только начиная с завтрашнего дня."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой."},
                {6, "Да, пока самокат не привезли."},
                {7, "Да, обязательно. Всем самокатов!"}
        });
    }

    @Test
    public void testFAQQuestionAnswer() {
        mainPage.clickQuestion(questionIndex);

        String actualAnswer = mainPage.getAnswerText(questionIndex);
        assertTrue("Ответ для вопроса " + (questionIndex + 1) + " не содержит ожидаемый текст. " +
                        "Ожидалось: " + expectedAnswer + ", Получено: " + actualAnswer,
                actualAnswer.contains(expectedAnswer));
    }
}