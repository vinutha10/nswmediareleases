package utility;

import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class Driver {
    public static WebDriver driver;

    public static String HomePageURL = null;
    public static Scenario scenarioStatic = null;

    public void createDriver() throws Exception {

        System.out.println("Openning Browser:");
        // String exePath = "chromedriver";
        // System.setProperty("webdriver.chrome.driver", exePath);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");

        driver = new ChromeDriver(options);

        // Add 10 secs implicit wait for each web elements
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Add 30 secs for all page load
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();


        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        capability.setCapability(CapabilityType.BROWSER_VERSION, "latest");
        capability.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");
        capability.setCapability("resolution", "1920x1080");
        HomePageURL = "https://www.nsw.gov.au/";
    }

    // close browser
    public void closeBrowser() {
        if (driver != null) {
            System.out.println("Closing Browser:");
            driver.quit();
        }
    }

}
