package org.example.tests.cadastro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
//        driver.quit();
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


    //testes de equivalência para campo de entrada 'Nome' :
    private void testarPreenchimentoNome(String entrada, String esperado) {
        cadastroPage.preencherNome(entrada);
        String valorAtual = cadastroPage.getNomeValue();
        Assertions.assertEquals(esperado, valorAtual, "O campo 'Nome' não contém o valor esperado!");
    }

    //VÁLIDOS
    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string")
    public void testeNomePadrao(){
        testarPreenchimentoNome("Tiago", "Tiago");
    }


    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string com espaços")
    public void testNomeComEspacos() {
        testarPreenchimentoNome("Tiago de Lemos", "Tiago de Lemos");
    }


    //INVÁLIDOS
    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string com caracteres especiais")
    public void testNomeComCaracteresEspeciais() {
        testarPreenchimentoNome("Jo@o-Silva!", "Jo@o-Silva!");
    }

    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string com números")
    public void testNomeComNumeros() {
        testarPreenchimentoNome("João123", "João123");
    }

    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita uma string vazia")
    public void testNomeStringVazia() {
        testarPreenchimentoNome("", "");
    }

    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita quebra de linha")
    public void testNomeComQuebraDeLinha() {
        String nomeComQuebraDeLinha = "Tiago\\nLemos \\ud83c\\udf89 \\ud83e\\udd84";
        testarPreenchimentoNome(nomeComQuebraDeLinha, nomeComQuebraDeLinha);
    }

    @Test
    @DisplayName("Verifica se o campo 'Nome' aceita string longa")
    public void testNomeStringLonga() {
        String nomeLongo = "a".repeat(100000);  //dá bug
        testarPreenchimentoNome(nomeLongo, nomeLongo);
    }

//    @Test
//    @DisplayName("Verifica se o campo 'Nome' não aceita valor nulo")
//    public void testNomeNull() {
//        cadastroPage.nomeInput.clear();
//        Assertions.assertTrue(cadastroPage.isNomeInputVisible(), "O campo 'Nome' não gerou erro ao ser preenchido com valor nulo!");
//    }

    //-------------------------------------------------------------------------------------

    //testes de equivalência para campo de entrada 'Idade' :
    private void testarPreenchimentoIdade(Integer entrada, Integer esperado) {
        cadastroPage.preencherIdade(entrada);
        Integer valorAtual = cadastroPage.getIdadeValue();
        Assertions.assertEquals(esperado, valorAtual, "O campo 'Idade' não contém o valor esperado!");
    }

    @Test
    @DisplayName("Verifica se idade aceita um número inteiro de 0 à 122")
    public void testIdadeValida() {
        testarPreenchimentoIdade(10, 10);
    }

    @Test
    @DisplayName("Verifica se idade aceita um número inteiro 0 (para meses)")
    public void testIdadeNumeroZero() {
        testarPreenchimentoIdade(0, 0);
    }

    @Test
    @DisplayName("Verifica se idade aceita um número inteiro 122 (maior idade registrada)")
    public void testIdadeNumeroMaior() {
        testarPreenchimentoIdade(122, 122);
    }







}
