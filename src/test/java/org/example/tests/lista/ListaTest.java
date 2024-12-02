package org.example.tests.lista;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.example.pages.ListaPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.asynchttpclient.util.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListaTest {
    private WebDriver driver;
    private ListaPage listaPage;
    private CadastroPage cadastroPage;
    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        listaPage = PageFactory.initElements(driver, ListaPage.class);
        driver.get("https://tc-1-final-parte1.vercel.app/tabelaCadastro.html");
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }


    @Test
    @DisplayName("Verificar título da página")
    public void testTitle() {
        String title = listaPage.getTituloLista(); // Obtém o texto do elemento 'tituloLista'
        assertEquals("Pessoas Cadastradas", title, "O título da página deve ser 'Pessoas Cadastradas'.");
    }



    @Test
    @DisplayName("Verificar se a lista de pessoas está vazia")
    public void testListaVazia() {
        assertTrue(listaPage.getListaPessoas().getText().isEmpty(), "A lista de pessoas deve estar vazia.");
    }

    @Test
    @DisplayName("Verificar se o botão 'Voltar para Cadastro' está visível")
    public void testBotaoVoltarParaCadastroVisivel() {
        assertTrue(listaPage.voltarParaCadastroButton.isDisplayed(), "O botão 'Voltar para Cadastro' deve estar visível.");
    }


    @Test
    @DisplayName("Adicionar múltiplas pessoas e verificar ordem")
    public void testAdicionarMultiplasPessoas() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify(["
                + "{ nome: 'Ana', idade: 20 }, { nome: 'Bruno', idade: 25 }, { nome: 'Clara', idade: 30 }"
                + "]))");
        driver.navigate().refresh();

        int indiceAna = listaPage.encontrarIndicePessoa("Ana");
        int indiceBruno = listaPage.encontrarIndicePessoa("Bruno");
        int indiceClara = listaPage.encontrarIndicePessoa("Clara");

        assertTrue(indiceAna < indiceBruno && indiceBruno < indiceClara, "A ordem deve ser 'Ana', 'Bruno', 'Clara'.");
    }

    @Test
    @DisplayName("Exibir botão 'Editar' para cada pessoa na lista")
    public void testExibirBotaoEditar() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'Marcio', idade: 25 }, { nome: 'Mariana', idade: 30 }]))");
        driver.navigate().refresh();

        int indiceMarcio = listaPage.encontrarIndicePessoa("Marcio");
        int indiceMariana = listaPage.encontrarIndicePessoa("Mariana");

        assertNotNull(listaPage.getBotaoEditar(indiceMarcio), "Botão 'Editar' deve estar presente para Marcio.");
        assertNotNull(listaPage.getBotaoEditar(indiceMariana), "Botão 'Editar' deve estar presente para Mariana.");
    }

    @Test
    @DisplayName("Exibir botão 'Remover' para cada pessoa na lista")
    public void testExibirBotaoRemover() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'João', idade: 25 }, { nome: 'Maria', idade: 30 }]))");
        driver.navigate().refresh();

        int indiceJoao = listaPage.encontrarIndicePessoa("João");
        int indiceMaria = listaPage.encontrarIndicePessoa("Maria");

        assertNotNull(listaPage.getBotaoExcluir(indiceJoao), "Botão 'Remover' deve estar presente para João.");
        assertNotNull(listaPage.getBotaoExcluir(indiceMaria), "Botão 'Remover' deve estar presente para Maria.");
    }

    @Test
    @DisplayName("Navegar de volta para a página de cadastro")
    public void testNavegacaoCadastro() {
        listaPage.voltarParaCadastroButton.click();
        assertTrue(driver.getCurrentUrl().endsWith("index.html"), "Deve navegar de volta para a página de cadastro.");
    }

    @Test
    @DisplayName("Persistência de dados ao voltar de outra página")
    public void testPersistenciaAoVoltar() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'Marcela', idade: 27 }]))");
        driver.navigate().refresh();
        listaPage.clicarVoltar();
        driver.navigate().back();

        WebElement lista = listaPage.getListaPessoas();
        assertTrue(lista.getText().contains("Marcela"), "A lista deve conter 'Marcela' ao voltar para a página.");
    }

    @Test
    @DisplayName("Persistência de dados após recarregar")
    public void testPersistenciaDados() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'Eduarda', idade: 28 }]))");
        driver.navigate().refresh();

        WebElement lista = listaPage.getListaPessoas();
        assertTrue(lista.getText().contains("Eduarda"), "A lista deve exibir 'Eduarda' após recarregar.");
    }

    @Test
    @DisplayName("Não exibir botão 'Remover' em lista vazia")
    public void testNaoExibirRemoverBotaoEmListaVazia() {
        WebElement lista = listaPage.getListaPessoas();
        assertNotNull(lista, "A lista de pessoas não deveria ser nula.");

        List<WebElement> removerButtons = listaPage.getRemoveButtons();
        assertTrue(removerButtons.isEmpty(), "Não deveria existir botão de remover em uma lista vazia.");
    }

    @Test
    @DisplayName("Verificar estado da página ao voltar para o cadastro")
    public void testBotaoVoltarParaCadastroEstadoPagina() {
        listaPage.clicarVoltar();
        WebElement nomeInput = driver.findElement(By.id("nome"));
        WebElement idadeInput = driver.findElement(By.id("idade"));
        assertTrue(nomeInput.getText().isEmpty(), "O campo nome deve estar vazio.");
        assertTrue(idadeInput.getText().isEmpty(), "O campo idade deve estar vazio.");
    }

    @Test
    @DisplayName("Exibir pessoas cadastradas na lista")
    public void testExibicaoDePessoas() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('pessoas', JSON.stringify([{ nome: 'João', idade: 25 }, { nome: 'Maria', idade: 30 }]))");
        driver.navigate().refresh();

        WebElement lista = listaPage.getListaPessoas();
        assertTrue(lista.getText().contains("João"), "A lista deve conter 'João'.");
        assertTrue(lista.getText().contains("Maria"), "A lista deve conter 'Maria'.");
    }


    @Test
    @DisplayName("Testar link 'Voltar para Cadastro'")
    public void testLinkVoltarParaCadastro() {
        listaPage.voltarParaCadastroButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertTrue(driver.getCurrentUrl().contains("index.html"), "A navegação deve levar à página index.html.");
    }



}
