package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

@Log4j2
public class WebDriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Configuration config = Configuration.getInstance();

    public static void setDriver() {
        String browserName = config.getBrowser().toLowerCase();
        WebDriver driver = createDriver(browserName);

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

        if(config.shouldMaximizeWindow()){
            driver.manage().window().maximize();
        }

        driverThreadLocal.set(driver);
        log.info("WebDriver initialization for browser: {}", browserName);

    }

    private static WebDriver createDriver(String browserName) {
        WebDriver driver;

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if(config.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-extensions");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(config.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported");
        }

        return driver;
    }

    public static WebDriver getDriver(){
        return  driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if( driver != null ) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("WebDriver closed successfully");
        }
    }
}
