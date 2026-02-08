package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

    // Поле "Имя"
    private final By firstName = By.xpath(".//input[@placeholder='* Имя']");
    // Поле "Фамилия"
    private final By lastName = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле "Адрес"
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле "Станция метро"
    private final By metroStation = By.xpath(".//input[@placeholder='* Станция метро']");
    // Поле "Телефон"
    private final By phone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее»
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Поле "Когда привезти"
    private final By dateOrder = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Поле "Срок аренды"
    private final By rental = By.className("Dropdown-control");

    // Кнопка "Заказать" (в окне заказа)
    //private final By finalOrderButton = By.xpath(".//div[@class='Order_Buttons__1x1BT']/button[text()='Заказать']");
    private final By finalOrderButton = By.xpath(".//div[contains(@class, 'Order_Content')]//button[text()='Заказать']");

    // Кнопка "Да"
    //private final By yesButton = By.xpath(".//button[text()='Да']");
    private final By yesButton = By.xpath(".//button[contains(@class, 'Button_Middle') and text()='Да']");

    // Заголовок успешного заказа
    private final By successHeader = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) { this.driver = driver; }

    public void fillData(String name, String surname, String addr, String metro, String tel) {
        driver.findElement(firstName).sendKeys(name);
        driver.findElement(lastName).sendKeys(surname);
        driver.findElement(address).sendKeys(addr);
        driver.findElement(metroStation).click();
        driver.findElement(By.xpath(".//div[text()='" + metro + "']")).click();
        driver.findElement(phone).sendKeys(tel);
        driver.findElement(nextButton).click();
    }

    public void fillRentData(String date, String duration, String color, String comment) {
        driver.findElement(dateOrder).sendKeys(date, Keys.ENTER);
        driver.findElement(rental).click();

        //driver.findElement(By.xpath(".//div[text()='" + duration + "']")).click();

        // Ожидание появления выпадающего списка срока аренды
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(".//div[text()='" + duration + "']"))).click();

        driver.findElement(By.id(color)).click();

        // Клик по кнопке "Заказать"
        driver.findElement(finalOrderButton).click();

        // ОЖИДАНИЕ появления кнопки "Да"
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        driver.findElement(yesButton).click();

    }

    public boolean checkOrderSuccess() {
        return driver.findElement(successHeader).getText().contains("Заказ оформлен");
    }
}
