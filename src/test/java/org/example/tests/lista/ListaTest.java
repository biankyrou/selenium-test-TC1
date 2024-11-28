package org.example.tests.lista;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.example.pages.ListaPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class ListaTest {
    private WebDriver driver;
    private ListaPage listaPage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        listaPage = PageFactory.initElements(driver, ListaPage.class);
        driver.get("https://tc-1-final-parte1.vercel.app/");
    }

    @AfterEach
    public void tearDown(){
//        driver.quit();
    }
}
