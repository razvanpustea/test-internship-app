package com.example.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public final class SeleniumInitializer {

    private SeleniumInitializer() {}

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/razvanpustea/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000L));

        return driver;
    }

    public static void sleep() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
