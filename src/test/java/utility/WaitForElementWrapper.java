package utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitForElementWrapper {

    public WebElement waitForElementToBeVisible(WebDriver driver, By by, Duration TimeForWaitInSeconds) throws Exception {
        WebElement element = null;
        try {
            element = (new WebDriverWait(driver, TimeForWaitInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));

        } catch (Exception e) {

            e.printStackTrace();
            throw new Exception("Element identified by " + by.toString() + " was not visible after "+TimeForWaitInSeconds+" seconds");
        }

        return element;
    }
}
