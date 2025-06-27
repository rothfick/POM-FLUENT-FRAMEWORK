package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class LoginPage extends BasePage{
    //Locator
    private static final By USERNAME_INPUT = By.name("username");
    private static final By PASSWORD_INPUT = By.name("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By ERROR_MESSAGE = By.cssSelector(".oxd-alert-content-text");
    private static final By FORGOT_PASSWORD_LINK = By.cssSelector(".orangehrm-login-forgot-header");


    @Step("Navigate to login page")
    public LoginPage navigateToLoginPage() {
        navigateToUrl(config.getLoginUrl());
        log.info("Navigated to login page");
        return this;
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username){
        typeText(USERNAME_INPUT, username);
        log.info("Enter username: {}", username);
        return this;
    }

    @Step("Enter password:{password}")
    public LoginPage enterPassword(String password) {
        typeText(PASSWORD_INPUT, password);
        log.info("Enter password:{}", password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLoginButton() {
        click(LOGIN_BUTTON);
        log.info("Click login button");
        return this;
    }

}
