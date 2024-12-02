package org.example.tests.lista;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;


@Suite
@SelectClasses({
        ListaTest.class
})
public class ListaSuiteTest {

    private WebDriver driver;
    private CadastroPage cadastroPage;
    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        cadastroPage = PageFactory.initElements(driver, CadastroPage.class);
        driver.get("https://tc-1-final-parte1.vercel.app/tabelaCadastro.html");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }



    @Test
    @DisplayName("Exibir botão 'Editar' para cada pessoa na lista")
    public void testExibirBotaoEditar() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'Marcio', idade: 25 }, { nome: 'Mariana', idade: 30 }]))");
        driver.navigate().refresh();

        List<WebElement> editarButtons = driver.findElements(By.xpath("//button[text()='Editar']"));
        assertEquals(2, editarButtons.size(), "Deve existir um botão 'Editar' para cada pessoa na lista.");
    }

    @Test
    @DisplayName("Exibir botão 'Remover' para cada pessoa na lista")
    public void testExibirBotaoRemover() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'João', idade: 25 }, { nome: 'Maria', idade: 30 }]))");
        driver.navigate().refresh();

        List<WebElement> removerButtons = driver.findElements(By.xpath("//button[text()='Remover']"));
        assertEquals(2, removerButtons.size(), "Deve existir um botão 'Remover' para cada pessoa na lista.");
    }





}
