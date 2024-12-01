package org.example.tests.lista;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void testTitle() {

        String title = driver.getTitle();
        assertEquals("Pessoas Cadastradas", title);
    }


    @Test
    public void testListaVazia() {
        // Verificar se a lista está vazia ao carregar a página
        WebElement lista = driver.findElement(By.id("listaPessoas"));
        assertTrue(lista.getText().isEmpty(), "A lista de pessoas deve estar vazia.");
    }

    @Test
    public void testBotaoVoltarParaCadastroVisivel() {
        WebElement voltarButton = driver.findElement(By.linkText("Voltar para Cadastro"));
        assertTrue(voltarButton.isDisplayed());
    }

    @Test
    public void testExibicaoDePessoas() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'João', idade: 25 }, { nome: 'Maria', idade: 30 }]))");
        driver.navigate().refresh();

        WebElement lista = driver.findElement(By.id("listaPessoas"));
        assertTrue(lista.getText().contains("João"));
        assertTrue(lista.getText().contains("Maria"));
    }

    @Test
    public void testNavegacaoCadastro() {
        WebElement botaoVoltar = driver.findElement(By.cssSelector(".btn"));
        botaoVoltar.click();
        assertTrue(driver.getCurrentUrl().endsWith("index.html"));
    }

    @Test
    public void testLinkVoltarParaCadastro() {
        WebElement link = driver.findElement(By.linkText("Voltar para Cadastro"));
        link.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertTrue(driver.getCurrentUrl().contains("index.html"));
    }

    @Test
    public void testBotaoVoltarParaCadastroEstadoPagina() {
        WebElement voltarButton = driver.findElement(By.linkText("Voltar para Cadastro"));
        voltarButton.click();
        WebElement nomeInput = driver.findElement(By.id("nome"));
        WebElement idadeInput = driver.findElement(By.id("idade"));
        assertTrue(nomeInput.getText().isEmpty());
        assertTrue(idadeInput.getText().isEmpty());
    }





}
