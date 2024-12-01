package org.example.tests.lista;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Verificar título da página")
    public void testTitle() {
        String title = driver.getTitle();
        assertEquals("Pessoas Cadastradas", title);
    }


    @Test
    @DisplayName("Verificar se a lista de pessoas está vazia")
    public void testListaVazia() {

        WebElement lista = driver.findElement(By.id("listaPessoas"));
        assertTrue(lista.getText().isEmpty(), "A lista de pessoas deve estar vazia.");
    }

    @Test
    @DisplayName("Verificar se o botão 'Voltar para Cadastro' está visível")
    public void testBotaoVoltarParaCadastroVisivel() {
        WebElement voltarButton = driver.findElement(By.linkText("Voltar para Cadastro"));
        assertTrue(voltarButton.isDisplayed());
    }

    @Test
    @DisplayName("Exibir pessoas cadastradas na lista")
    public void testExibicaoDePessoas() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'João', idade: 25 }, { nome: 'Maria', idade: 30 }]))");
        driver.navigate().refresh();

        WebElement lista = driver.findElement(By.id("listaPessoas"));
        assertTrue(lista.getText().contains("João"));
        assertTrue(lista.getText().contains("Maria"));
    }

    @Test
    @DisplayName("Navegar de volta para a página de cadastro")
    public void testNavegacaoCadastro() {
        WebElement botaoVoltar = driver.findElement(By.cssSelector(".btn"));
        botaoVoltar.click();
        assertTrue(driver.getCurrentUrl().endsWith("index.html"));
    }

    @Test
    @DisplayName("Testar link 'Voltar para Cadastro'")
    public void testLinkVoltarParaCadastro() {
        WebElement link = driver.findElement(By.linkText("Voltar para Cadastro"));
        link.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertTrue(driver.getCurrentUrl().contains("index.html"));
    }

    @Test
    @DisplayName("Verificar estado da página ao voltar para o cadastro")
    public void testBotaoVoltarParaCadastroEstadoPagina() {
        WebElement voltarButton = driver.findElement(By.linkText("Voltar para Cadastro"));
        voltarButton.click();
        WebElement nomeInput = driver.findElement(By.id("nome"));
        WebElement idadeInput = driver.findElement(By.id("idade"));
        assertTrue(nomeInput.getText().isEmpty());
        assertTrue(idadeInput.getText().isEmpty());
    }

    @Test
    @DisplayName("Não exibir botão 'Remover' em lista vazia")
    public void testNaoExibirRemoverBotaoEmListaVazia() {
        WebElement listaPessoas = driver.findElement(By.id("listaPessoas"));
        List<WebElement> removerButtons = driver.findElements(By.xpath("//button[text()='Remover']"));
        assertTrue(removerButtons.isEmpty(), "Não deveria existir botão de remover na lista vazia.");
    }

    @Test
    @DisplayName("Adicionar pessoa à lista e verificar exibição")
    public void testAdicionarPessoa() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'Carlos', idade: 40 }]))");
        driver.navigate().refresh();

        WebElement lista = driver.findElement(By.id("listaPessoas"));
        assertTrue(lista.getText().contains("Carlos"), "A lista deveria conter 'Carlos'");
    }

    @Test
    @DisplayName("Adicionar múltiplas pessoas e verificar ordem")
    public void testAdicionarMultiplasPessoas() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify(["
                + "{ nome: 'Ana', idade: 20 }, { nome: 'Bruno', idade: 25 }, { nome: 'Clara', idade: 30 }"
                + "]))");
        driver.navigate().refresh();

        WebElement lista = driver.findElement(By.id("listaPessoas"));
        String textoLista = lista.getText();
        assertTrue(textoLista.contains("Ana") && textoLista.contains("Bruno") && textoLista.contains("Clara"),
                "Os nomes 'Ana', 'Bruno' e 'Clara' devem aparecer na lista.");
        assertTrue(textoLista.indexOf("Ana") < textoLista.indexOf("Bruno") && textoLista.indexOf("Bruno") < textoLista.indexOf("Clara"),
                "A ordem dos nomes deve ser 'Ana', 'Bruno' e 'Clara'.");
    }




}
