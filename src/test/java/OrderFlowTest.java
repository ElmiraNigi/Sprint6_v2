import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.util.stream.Stream;

public class OrderFlowTest  {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        // driver = new FirefoxDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void testFullOrderFlow(boolean isTopButton, String name, String surname, String addr, String metro, String phone, String date, String dur, String color) {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        if (isTopButton) mainPage.clickTopOrderButton();
        else mainPage.clickBottomOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillData(name, surname, addr, metro, phone);
        orderPage.fillRentData(date, dur, color, "Комментарий пользователя");

        Assertions.assertTrue(orderPage.checkOrderSuccess(), "Финальное окно 'Заказ оформлен' не появилось");
    }

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of(true, "Эльмира", "Зыби", "Лыжный переулок", "Красносельская", "79112345697", "10.02.2026", "трое суток", "black"),
                Arguments.of(false, "Лилия", "Кузеева", "Улица Ленина", "Чистые пруды", "89112365489", "17.02.2026", "пятеро суток", "grey")
        );
    }

    @AfterEach
    public void tearDown()
    { driver.quit(); }
}