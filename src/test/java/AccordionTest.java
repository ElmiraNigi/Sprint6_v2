import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.MainPage;

public class AccordionTest {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        // driver = new FirefoxDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 'Сутки — 400 рублей. Оплата курьеру — наличными или картой.'",
            "5, 'Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.'"
    })
    public void testAccordionQuestions(int index, String expectedText) {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickAccordionQuestion(index);
        Assertions.assertEquals(expectedText, mainPage.getAccordionAnswerText(index));
        String actualText = mainPage.getAccordionAnswerText(index);
        Assertions.assertTrue(actualText.startsWith(expectedText),
                "Ожидалось: " + expectedText + ", а получили: " + actualText);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}