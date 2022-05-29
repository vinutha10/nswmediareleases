package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features/MediaReleasesPageTest.feature",
        glue = "stepDefinations",
        plugin = {"pretty","html:target/rerun-cucumber-reports","json:target/rerun-cucumber.json"},
        tags = "@ministerFilter @mediaReleases @filterReset",
        monochrome = true)

public class TestRunner {
}
