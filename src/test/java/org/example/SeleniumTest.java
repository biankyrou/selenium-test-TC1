package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTest {
    private WebDriver driver;
    @BeforeEach
    void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @AfterEach
    void tearDown(){
        driver.quit();
    }

    @Test
    @DisplayName("Should open Google and search")
    void shouldOpenGoogleAndSearch() throws InterruptedException {
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        Thread.sleep(200);
        driver.findElement(By.className("gNO89b")).click();// the search button class is "gNO89b"
        Thread.sleep(600);
    }

    @Test
    @DisplayName("Should result page title start with searched text and end with Pesquisa Google")
    void shouldPageTitleStartWithSearchedTextAndEndWithPesquisaGoogle()throws InterruptedException {
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        Thread.sleep(200);
        driver.findElement(By.className("gNO89b")).click();
        Thread.sleep(200);
        final String title = driver.getTitle(); // obtains the title of the page
        assertThat(title).isEqualTo("Selenium WebDriver - Pesquisa Google");
    }
}


