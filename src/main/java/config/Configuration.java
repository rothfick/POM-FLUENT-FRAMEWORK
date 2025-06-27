package config;


import lombok.Getter;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Getter
public class Configuration {
    private static Configuration instance;
    private final Properties properties;

    public Configuration() {
        properties = new Properties();
        //Need to implement method LoadProperties();
        loadProperties();
    }

    public static Configuration getInstance(){
        if(instance == null) {
            synchronized (Configuration.class) {
                if(instance == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
    }

    private void loadProperties(){
        try(InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("config.properties")){
            if (inputStream != null) {
                properties.load(inputStream);
                log.info("Configuration loaded Successfully");
            }else {
                log.error("Configuration loaded Failed");
                throw new RuntimeException("Configuration file not found");
            }

        } catch (IOException e) {
            log.error("Error during loading configuration: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getBrowser(){
        return properties.getProperty("browser", "chrome");
    }

    public boolean isHeadless(){
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public boolean shouldMaximizeWindow(){
        return Boolean.parseBoolean(properties.getProperty("window.maximize", "true"));
    }

    public int getImplicitWait(){
        return Integer.parseInt(properties.getProperty("implicit.wait", "7"));
    }

    public int getExplicitWait(){
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public String getBaseUrl(){
        return properties.getProperty("base.url");
    }

    public String getLoginUrl(){
        return properties.getProperty("login.url");
    }

    public String getTestUsername(){
        return properties.getProperty("test.username");
    }

    public String getTestPassword(){
        return properties.getProperty("test.password");
    }
}
