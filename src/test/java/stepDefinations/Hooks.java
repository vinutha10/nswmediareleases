package stepDefinations;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import utility.Driver;

import java.lang.management.ManagementFactory;

public class Hooks extends Driver {

    Driver Df = new Driver();

    @Before
    public void startWebBrowser(Scenario scenario) throws Exception {
        scenarioStatic = scenario;
        long threadId = Thread.currentThread().getId();
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);
        Df.createDriver();
    }

    @After
    public void closeWebBrowerAndTakeScreenShotOnFailure(Scenario scenario) {
        SessionId sessionID = ((RemoteWebDriver) driver).getSessionId();
        if (scenario.isFailed() && sessionID != null) {

            try {

                final byte[] screenshotAferException = ((TakesScreenshot) Driver.driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshotAferException, "image/png");

            } catch (WebDriverException e) {
                e.printStackTrace();
            }

        }
        // only call if drive in null

        if (sessionID != null) {
            scenario.write(" Current URL After exectuion : " + driver.getCurrentUrl());
            Df.closeBrowser();
        }
    }
}
