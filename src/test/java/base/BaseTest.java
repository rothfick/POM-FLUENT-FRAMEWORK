package base;

import config.Configuration;
import config.WebDriverFactory;
import lombok.extern.log4j.Log4j2;
import models.User;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.LoginPage;

@Log4j2
public class BaseTest {

    protected Configuration config;
    protected LoginPage loginPage;
    protected User user;

    @BeforeSuite
    public void beforeSuite() {
        config = Configuration.getInstance();

        //Implementation of Allure methods
    }

    @BeforeMethod
    public void beforeMethod() {
        WebDriverFactory.setDriver();
        loginPage = new LoginPage();
        user = User.getTestUser();
        log.info("Test setup completed with success");
    }

    @AfterMethod
    public void afterMethod() {
        WebDriverFactory.quitDriver();
    }
}
