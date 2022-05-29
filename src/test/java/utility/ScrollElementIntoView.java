package utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollElementIntoView {
    public void scrollElement(WebDriver driver,WebElement element){
        // Create instance of Javascript executor and down cast web driver to a Java script executor instance
        JavascriptExecutor je = (JavascriptExecutor) driver;
        // now execute query which actually will scroll until that element is not visible on page.
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        //System.out.println("Java script scrolling !!!");

    }

}
