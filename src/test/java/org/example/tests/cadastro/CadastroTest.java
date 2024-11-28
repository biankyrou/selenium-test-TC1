package org.example.tests.cadastro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CadastroTest {
    private WebDriver driver;
    private CadastroPage cadastroPage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        cadastroPage = PageFactory.initElements(driver, CadastroPage.class);
        driver.get("https://tc-1-final-parte1.vercel.app/");
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }


    // testes funcionais:
    @Test
    @DisplayName("Verifica se o campo \"Nome\" está visível")
    public void testNomeInputVisivel() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='nome']")));
        Assertions.assertTrue(cadastroPage.isNomeInputVisible(), "O campo Nome não está visível!");
    }

    @Test
    @DisplayName("Verifica se o campo \"Idade\" está visível")
    public void testIdadeInputVisivel() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idade']")));
        Assertions.assertTrue(cadastroPage.isIdadeInputVisible(), "O campo Idade não está visível!");
    }

    @Test
    @DisplayName("Verifica se o Cadastrar Button está visível")
    public void testCadastrarButtonVisivel() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cadastroForm\"]/button")));
        Assertions.assertTrue(cadastroPage.isCadastrarButtonVisible(), "O botão Cadastrar não está visível!");
    }

    @Test
    @DisplayName("Verifica se o Visualizar Lista Button está visível")
    public void testVisualizarButtonVisivel() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/a")));
        Assertions.assertTrue(cadastroPage.isVisualizarButtonVisible(), "O botão Visualizar não está visível!");
    }


    @Test
    @DisplayName("Verifica se os campos de input e botões estão visíveis")
    public void testCadastroPageCarregada() {
        Assertions.assertTrue(cadastroPage.isNomeInputVisible());
        Assertions.assertTrue(cadastroPage.isIdadeInputVisible());
        Assertions.assertTrue(cadastroPage.isCadastrarButtonVisible());
        Assertions.assertTrue(cadastroPage.isVisualizarButtonVisible());
    }


    //testes de equivalência VÁLIDOS para campo de entrada 'Nome' :
    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string com caracteres especiais")
    public void testNomeValidoComCaracteresEspeciais() {
        cadastroPage.preencherNome("Jo@o-Silva!");
        Assertions.assertTrue(cadastroPage.isNomeInputVisible());
    }

    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string com números")
    public void testNomeValidoComNumeros() {
        cadastroPage.preencherNome("João123");
        Assertions.assertTrue(cadastroPage.isNomeInputVisible());
    }




    //testes de equivalência INVÁLIDOS para campo de entrada 'Nome' :



}
