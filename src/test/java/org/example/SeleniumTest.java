package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); //sets up a valid driver for Chrome.
// WebDriverManager.operadriver().setup(); // do the same for Opera browser
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }


}


