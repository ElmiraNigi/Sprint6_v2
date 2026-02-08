package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Кнопка куки «да все привыкли»
    private final By cookieButton = By.id("rcc-confirm-button");

    // Кнопка «Заказать». Сверху
    private final By topOrderButton = By.className("Button_Button__ra12g");

    // Кнопка «Заказать». Внизу
    //private final By bottomOrderButton = By.className("Button_Button__ra12g Button_Middle__1CSJM");
    //private final By bottomOrderButton = By.xpath(".//div[@class='Home_FinishButton__1E9ov']/button");
    private final By bottomOrderButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookies() {
        if (!driver.findElements(cookieButton).isEmpty()) {
            driver.findElement(cookieButton).click();
        }
    }

    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        /* так как кнопка внизу, скроллим до самого низа, пока её не найдём */
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);

        // Ожидание, что кнопка стала кликабельной после скролла
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(element));

        element.click();
    }

    public void clickAccordionQuestion(int index) {
        By question = By.id("accordion__heading-" + index);
        WebElement element = driver.findElement(question);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    public String getAccordionAnswerText(int index) {
        By answer = By.id("accordion__panel-" + index);

        // Ждем до 3 секунд, пока текст ответа станет видимым (после анимации)
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(answer));

        return driver.findElement(answer).getText();
    }

}

