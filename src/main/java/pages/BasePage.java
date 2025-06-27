package pages;

import config.Configuration;
import config.WebDriverFactory;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Log4j2
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Configuration config;

    public BasePage(){
        this.driver = WebDriverFactory.getDriver();
        this.config = Configuration.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    //implementacja metod bazowych
    @Step("Wait for element to be visible: {locator}")
    protected WebElement waitForElementVisible(By locator){
        log.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Wait for page to load")
    protected BasePage waitForPageToLoad(){
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        log.debug("Page loaded completely");
        return this;
    }

    @Step("Navigate to URL: {url}")
    protected BasePage navigateToUrl(String url){
        driver.get(url);
        waitForPageToLoad();
        log.info("Navigated to URL: {}", url);
        return this;
    }

    @Step("Type text: {text} into element: {locator}")
    protected void typeText(By locator, String text){
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        log.debug("Type text into element: {}", text);
    }

    @Step("Click into element:{locator}")
    protected BasePage click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        return this;
    }


    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
