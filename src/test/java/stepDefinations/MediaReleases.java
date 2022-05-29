package stepDefinations;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utility.Driver;
import utility.ScrollElementIntoView;
import utility.WaitForElementWrapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

public class MediaReleases extends Driver{
    // Instantiate Scroll to element
    ScrollElementIntoView scrollToElementToView = new ScrollElementIntoView();
    WaitForElementWrapper waitForElement = new WaitForElementWrapper();

    @Given("^I navigate to media releases page$")
    public void i_navigate_to_media_releases_page() {
        driver.get(HomePageURL + "media-releases");
    }

    @When("^I see \"([^\"]*)\" dropdown is present and click expand$")
    public void iSeeDropdownIsPresentAndClickExpand(String filterText) throws Throwable {
            WebElement filterByMinister = waitForElement.waitForElementToBeVisible(driver,
                    By.xpath("//button[contains(text(),'Filter by Minister')]"), Duration.ofSeconds(20));
            filterByMinister.click();
            Thread.sleep(1000);
    }

    @Then("^I should verify all (\\d+) ministers are present$")
    public void iShouldVerifyAllMinistersArePresent(int count) throws Throwable{

        // find expanded Category lists on the page
        List<WebElement> categoryMLists = driver
                .findElements(By.xpath("//label[@class='nsw-form__checkbox-label option']"));

        for (WebElement categoryLists : categoryMLists) {
            scrollToElementToView.scrollElement(driver, categoryLists);
            //Assert that Companion Category Lists is NOT empty
            Assert.assertFalse("Category Lists is not empty", categoryLists.getText().isEmpty());
            Assert.assertFalse("Category Lists are not reset",categoryLists.isSelected());
            int xpathCount = driver.findElements(By.xpath("//label[@class='nsw-form__checkbox-label option']")).size();
            Assert.assertTrue("Default 14 categories are not shown", xpathCount <= count);
        }
        Thread.sleep(1000);
    }

    @When("^I select \"([^\"]*)\" filter and click search$")
    public void i_select_filter_and_click_search(String categoryText) throws Throwable {
        // Click on categoryText
        WebElement categoryBox = driver.findElement(By.xpath("//label[contains(text(),'" + categoryText + "')]"));
        scrollToElementToView.scrollElement(driver, categoryBox);
        categoryBox.click();

        WebElement searchButton =  waitForElement.waitForElementToBeVisible(driver,
                By.xpath("//input[@id='edit-submit-media-release']"), Duration.ofSeconds(20));
        searchButton.click();
        Thread.sleep(1000);
    }

    @Then("^I should see \"([^\"]*)\" displayed as tags on media release page$")
    public void iShouldSeeDisplayedAsTagsOnMediaReleasePage(String categoryText) throws Throwable {
        // wait at least 10 sec for search results page to load
        waitForElement.waitForElementToBeVisible(driver,
                By.xpath("//div[@class='nsw-col search-grid__results margin-top--sm']"), Duration.ofSeconds(20));
        // find Category as Tags on the search result page
        List<WebElement> TagsList = driver
                .findElements(By.xpath("//div[@class='nsw-card__tag']"));

        for (WebElement Tags : TagsList) {
            scrollToElementToView.scrollElement(driver, Tags);
            Assert.assertTrue("Companion card listing result should have Tags selected from Category Check Boxes",
                    Tags.getText().trim().contains(categoryText));
        }
    }

    @When("^I click on Reset Filter button$")
    public void iClickOnResetFilterButton() throws Throwable {
        // wait at least 10 sec for element to be visible
        WebElement reset = waitForElement.waitForElementToBeVisible(driver,
                By.xpath("//input[@value='Reset']"), Duration.ofSeconds(30));
        reset.click();
        Thread.sleep(1000);
    }

    @Then("^I should see all filters are cleared$")
    public void i_should_see_all_filters_are_cleared() throws Throwable {
        WebElement searchTextBoxElement = driver.findElement(By.name("combine"));
        scrollToElementToView.scrollElement(driver, searchTextBoxElement);
        assertThat("Search Textbox is not cleared",
               searchTextBoxElement.getAttribute("value"), emptyString());
        this.iSeeDropdownIsPresentAndClickExpand("Filter by Minister");
        this.iShouldVerifyAllMinistersArePresent(14);
        Thread.sleep(1000);
    }

    @And("^I should see the results are matching as I landed on media releases page$")
    public void iShouldSeeTheResultsAreMatchingAsWhenILandedOnMediaReleasesPage() throws Throwable{
        List<String> newMediaTags = new ArrayList<>();
        // wait at least 10 sec for Media list page to load
        waitForElement.waitForElementToBeVisible(driver,
                By.xpath("//div[@class='nsw-col search-grid__results margin-top--sm']"), Duration.ofSeconds(20));
        // find Media Tags element
        List<WebElement> mediaTagList = driver
                .findElements(By.xpath("//div[@class='nsw-card__tag']"));
        for (WebElement mediaTag : mediaTagList) {
            scrollToElementToView.scrollElement(driver, mediaTag);
            // Assert that Media Tag is NOT empty
            Assert.assertFalse("Media Tag is empty", mediaTag.getText().isEmpty());
            // add Media tag to List
            newMediaTags.add(mediaTag.getText());
        }
        // Assert that Original Companion tag list and from refresh are same or not
        Assert.assertEquals("Results are not matching", newMediaTags, originalMediaTags);
    }

    //Store original media tags
    List<String> originalMediaTags = new ArrayList<>();

    @Then("^I should see tags on the page$")
    public void iShouldSeeTitleOnThePage() throws Throwable {
    //    this.iSeeDropdownIsPresentAndClickExpand("Filter by Minister");

        // find Category as Tags on the search result page
        List<WebElement> TagsList = driver
                .findElements(By.xpath("//div[@class='nsw-card__tag']"));

        for (WebElement Tags : TagsList) {
            scrollToElementToView.scrollElement(driver, Tags);
            Assert.assertFalse("Companion card listing result should have Tags on the cards displayed",
                    Tags.getText().isEmpty());
            // add blog title to List
            originalMediaTags.add(Tags.getText());
        }
    }
}