package org.example.tests.cadastro;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.CadastroPage;
import org.example.pages.ListaPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CadastroTest {
    private WebDriver driver;
    private CadastroPage cadastroPage;
    private final Faker faker = new Faker();

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


    @Nested
    @DisplayName("Testes Funcionais que verificam visibilidade de elementos")
    class TestesFuncionaisVisibilidade{
        // testes funcionais:
        @Test
        @DisplayName("Verifica se o campo 'Nome' está visível")
        public void testNomeInputVisivel() {
            cadastroPage.waitForNomeInput();
            Assertions.assertTrue(cadastroPage.isNomeInputVisible(), "O campo Nome não está visível!");
        }

        @Test
        @DisplayName("Verifica se o campo 'Idade' está visível")
        public void testIdadeInputVisivel() {
            cadastroPage.waitForIdadeInput();
            Assertions.assertTrue(cadastroPage.isIdadeInputVisible(), "O campo Idade não está visível!");
        }

        @Test
        @DisplayName("Verifica se o Cadastrar Button está visível")
        public void testCadastrarButtonVisivel() {
            cadastroPage.waitForCadastrarButton();
            Assertions.assertTrue(cadastroPage.isCadastrarButtonVisible(), "O botão Cadastrar não está visível!");
        }

        @Test
        @DisplayName("Verifica se o Visualizar Lista Button está visível")
        public void testVisualizarButtonVisivel() {
            cadastroPage.waitForVisualizarButton();
            Assertions.assertTrue(cadastroPage.isVisualizarButtonVisible(), "O botão Visualizar não está visível!");
        }

        @Test
        @DisplayName("Verifica se os campos de input e botões estão visíveis")
        public void testCadastroPageCarregada() {
            cadastroPage.waitForNomeInput();
            cadastroPage.waitForIdadeInput();
            cadastroPage.waitForCadastrarButton();
            cadastroPage.waitForVisualizarButton();

            Assertions.assertTrue(cadastroPage.isNomeInputVisible());
            Assertions.assertTrue(cadastroPage.isIdadeInputVisible());
            Assertions.assertTrue(cadastroPage.isCadastrarButtonVisible());
            Assertions.assertTrue(cadastroPage.isVisualizarButtonVisible());
        }
    }

    @Test
    @DisplayName("Verifica comportamento ao colar valores com espaços extras no campo de nome")
    public void testColarValorComEspacosNome() {
        cadastroPage.waitForNomeInput();
        cadastroPage.colarNome("  João da Silva  ");
        String valorAtual = cadastroPage.getNomeValue();
        Assertions.assertEquals("  João da Silva  ", valorAtual, "O campo 'Nome' deve preservar espaços colados!");
    }

    @Test
    @DisplayName("Verifica comportamento ao colar texto simples no campo 'Nome'")
    public void testColarTextoSimplesNome() {
        cadastroPage.waitForNomeInput();
        cadastroPage.colarNome("Ana Paula");
        String valorAtual = cadastroPage.getNomeValue();
        Assertions.assertEquals("Ana Paula", valorAtual, "O campo 'Nome' deve aceitar valores simples colados!");
    }

    @Test
    @DisplayName("Verifica comportamento ao colar valores inválidos no campo de idade")
    public void testColarValorInvalido() {
        cadastroPage.waitForIdadeInput();
        cadastroPage.colarIdade("texto_invalido"); // Cola valor inválido
        Integer valorAtual = cadastroPage.getIdadeString(); // Tenta obter o valor convertido
        Assertions.assertNull(valorAtual, "O campo 'Idade' deve retornar null ao colar valores inválidos!");
    }

    @Test
    @DisplayName("Verifica comportamento ao colar valores válidos no campo de nome")
    public void testColarValorValidoNome() {
        cadastroPage.waitForNomeInput();
        cadastroPage.colarNome("João Silva");
        String valorAtual = cadastroPage.getNomeValue();
        Assertions.assertEquals("João Silva", valorAtual, "O campo 'Nome' deve aceitar valores válidos colados!");
    }

    @Test
    @DisplayName("Verifica comportamento ao colar valores inválidos no campo de nome")
    public void testColarValorInvalidoNome() {
        cadastroPage.waitForNomeInput();
        cadastroPage.colarNome("");
        String valorAtual = cadastroPage.getNomeValue();
        Assertions.assertTrue(valorAtual.isEmpty(), "O campo 'Nome' deve estar vazio ao colar valores inválidos!");
    }

    // TESTE TEMPO DE RESPOSTA
    @Test
    @DisplayName("Verifica o tempo de resposta do botão 'Cadastrar'")
    public void testTempoRespostaCadastrar() {
        long startTime = System.currentTimeMillis();
        cadastroPage.clicarCadastrar();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Assertions.assertTrue(duration < 2000, "A resposta do botão 'Cadastrar' demorou mais que 2 segundos!");
    }

    @Test
    @DisplayName("Verifica se a página inicial carrega rapidamente")
    public void testCarregamentoPaginaInicial() {
        long startTime = System.currentTimeMillis();
        driver.get("https://tc-1-final-parte1.vercel.app/");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Assertions.assertTrue(duration < 3000, "A página inicial demorou mais que 3 segundos para carregar!");
    }


    @Nested
    @DisplayName("Testes para campo de entrada 'Nome'")
    class TestCampoDeEntradaNome{
        //testes de equivalência para campo de entrada 'Nome' :

        private void testarPreenchimentoNome(String entrada, String esperado) {
            cadastroPage.waitForNomeInput();
            cadastroPage.preencherNome(entrada);
            String valorAtual = cadastroPage.getNomeValue();
            Assertions.assertEquals(esperado, valorAtual, "O campo 'Nome' não contém o valor esperado!");
        }
        @Nested
        @DisplayName("Dados VÁLIDOS")
        class TestDadosValidosNome{

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

        }
        @Nested
        @DisplayName("Dados INVÁLIDOS")
        class TestDadosInvalidosNome{

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
                String nomeLongo = "a".repeat(1000);  //com 100000 dá bug
                testarPreenchimentoNome(nomeLongo, nomeLongo);
            }
            @Test
            @DisplayName("Deve permitir preencher o campo 'Nome' até o limite de caracteres")
            void testarLimiteDeCaracteresNome() {
                String entrada = "NomeMuitoLongoDeExemploParaTeste";
                String esperado = "NomeMuitoLongoDeExemploParaTeste";
                cadastroPage.waitForNomeInput();
                cadastroPage.preencherNome(entrada);
                String valorAtual = cadastroPage.getNomeValue();
                Assertions.assertEquals(esperado, valorAtual, "O campo 'Nome' não deve exceder o limite de caracteres!");
            }
        }

    }

    //-------------------------------------------------------------------------------------

    @Nested
    @DisplayName("Testes para campo de entrada 'Idade'")
    class TestCampoDeEntradaIdade{

        //    testes de equivalência para campo de entrada 'Idade' :

        private void testarPreenchimentoIdade(Integer entrada, Integer esperado) {
            cadastroPage.waitForIdadeInput();
            cadastroPage.preencherIdade(entrada);
            Integer valorAtual = cadastroPage.getIdadeValue();
            Assertions.assertEquals(esperado, valorAtual, "O campo 'Idade' não contém o valor esperado!");
        }
        @Nested
        @DisplayName("Dados VÁLIDOS")
        class TestDadosValidosIdade{

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
        @Nested
        @DisplayName("Dados INVÁLIDOS")
        class TestDadosInvalidosIdade{
            //INVALIDOS

            @Test
            @DisplayName("Verifica se idade aceita um número negativo")
            public void testIdadeNumeroNegativo() {
                testarPreenchimentoIdade(-1, -1);
            }
            @Test
            @DisplayName("Verifica se idade aceita um número grande")
            public void testIdadeNumeroGrande() {
                testarPreenchimentoIdade(1000, 1000);
            }
        }

    }


    // -----------------PREENCHIMENTO CADASTRO-----------------------

    // CADASTRO COM SUCESSO VENDO POPUP

    @Test
    @DisplayName("Testa o preenchimento do cadastro com válores válidos")
    public void testPreenchimentoFormularioCadastro() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();
        cadastroPage.preencherCadastro("João Silva", 25);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        String mensagemPopUp = cadastroPage.getPopupMessageSuccess();
        Assertions.assertEquals("Sucesso!\n" +
                "Pessoa cadastrada com sucesso!\n" +
                "OK", mensagemPopUp);
    }


    @Test
    @DisplayName("Testa o preenchimento do cadastro com nome inválido")
    public void testPreenchimentoFormularioCadastroNomeInvalido() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("João-Silva123@@!", 25);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        String mensagemPopUp = cadastroPage.getPopupMessageSuccess();
        Assertions.assertEquals("Sucesso!\n" +
                "Pessoa cadastrada com sucesso!\n" +
                "OK", mensagemPopUp);
    }


    @Test
    @DisplayName("Testa o preenchimento do cadastro com idade inválida")
    public void testPreenchimentoFormularioCadastroIdadeInvalida() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();
        cadastroPage.preencherCadastro("Jinx Powder", -120);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        String mensagemPopUp = cadastroPage.getPopupMessageSuccess();
        Assertions.assertEquals("Sucesso!\n" +
                "Pessoa cadastrada com sucesso!\n" +
                "OK", mensagemPopUp);
    }

    @Test
    @DisplayName("Testa o preenchimento do cadastro com dados inválidos")
    public void testPreenchimentoFormularioCadastroDadosInvalidos() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("Ambessa060606! 329-1", 2191855);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        String mensagemPopUp = cadastroPage.getPopupMessageSuccess();
        Assertions.assertEquals("Sucesso!\n" +
                "Pessoa cadastrada com sucesso!\n" +
                "OK", mensagemPopUp);
    }


    // VERIFICANDO SE O CADASTRO FOI FEITO COM SUCESSO VENDO A LISTA DE PESSOAS

    @Test
    @DisplayName("Testa o preenchimento do cadastro valores válidos")
    public void testPreenchimentoCadastroDadosValidosLista() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("Violet", 25);
        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista("Violet");
        Assertions.assertTrue(pessoaCadastrada, "Pessoa cadastrada");
    }

    @Test
    @DisplayName("Testa navegação para a lista após cadastro bem-sucedido")
    public void testNavegacaoAposCadastro() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("Navegador", 30);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listaPage = new ListaPage(driver);
        boolean pessoaCadastrada = listaPage.isPessoaNaLista("Navegador");
        Assertions.assertTrue(pessoaCadastrada, "A pessoa não foi encontrada na lista após cadastro.");
    }


    //TESTES COM JAVA FAKER

    @Test
    @DisplayName("Testa o preenchimento do cadastro valores válidos")
    public void testPreenchimentoCadastfdddddroDadosValidosLista() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        String nome = faker.name().fullName();
        int idade = faker.number().numberBetween(18, 70);

        cadastroPage.preencherCadastro(nome, idade);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista(nome);
        Assertions.assertTrue(pessoaCadastrada, "Pessoa cadastrada");
    }

    @Test
    @DisplayName("Cadastro com dados excessivamente longos")
    public void testCadastroComDadosLongos() {
        Faker faker = new Faker();
        String nomeLongo = faker.lorem().characters(300);
        int idadeAleatoria = 35;

        cadastroPage.waitForNomeInput();
        cadastroPage.preencherCadastro(nomeLongo, idadeAleatoria);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();
    }

    @Test
    @DisplayName("Cadastro com nome contendo caracteres especiais")
    public void testCadastroComNomeInvalido() {
        Faker faker = new Faker();
        String nomeInvalido = faker.regexify("[@#$%^&*()_+=]+");

        int idadeAleatoria = 28;

        cadastroPage.waitForNomeInput();
        cadastroPage.preencherCadastro(nomeInvalido, idadeAleatoria);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();
    }



    // FLUXO ONDE NÃO CADASTRA

    @Test
    @DisplayName("Testa o preenchimento do cadastro com nome null e verifica se cadastrou")
    public void testPreenchimentoFormularioCadastroNomeNull() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherIdade(30);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista("");
        Assertions.assertFalse(pessoaCadastrada, "Pessoa sem nome não deveria ser cadastrada");
    }

    @Test
    @DisplayName("Testa o preenchimento do cadastro com idade null e verifica se cadastrou")
    public void testPreenchimentoFormularioCadastroIdadeNull() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherNome("Fernanda Torres");

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista("Fernanda Torres");
        Assertions.assertFalse(pessoaCadastrada, "Pessoa sem idade não deveria ser cadastrada");
    }

    @Test
    @DisplayName("Testa o preenchimento do cadastro com idade e nomes nulos e verifica se cadastrou")
    public void testPreenchimentoFormularioCadastroDadosNulos() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista("");
        Assertions.assertFalse(pessoaCadastrada, "Pessoa sem dados não deveria ser cadastrada");
    }


    @Test
    @DisplayName("Testa o preenchimento do cadastro com nome vazio e idade válida e verifica se cadastrou")
    public void testCadastroComNomeVazioEIdadeValida() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherNome("");
        cadastroPage.preencherIdade(25);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForVisualizarButton();
        cadastroPage.clicarVisualizarPessoas();

        ListaPage listapage = new ListaPage(driver);
        boolean pessoaCadastrada = listapage.isPessoaNaLista("");
        Assertions.assertFalse(pessoaCadastrada, "Pessoa com nome vazio não deveria ser cadastrada");
    }


    ///////////////////////////////////////////////////////////
    //MOVER DE LUGAR: para fluxo que passa!

    @Test
    @DisplayName("Testa múltiplos cadastros consecutivos")
    public void testMultiplosCadastros() {
        for (int i = 1; i <= 3; i++) {
            cadastroPage.waitForNomeInput();
            cadastroPage.waitForIdadeInput();

            String nome = "Pessoa " + i;
            int idade = 20 + i;

            cadastroPage.preencherCadastro(nome, idade);

            cadastroPage.waitForCadastrarButton();
            cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
            cadastroPage.clicarCadastrar();

            cadastroPage.waitForPopupAndClickOkButton();
            Assertions.assertEquals("Sucesso!\nPessoa cadastrada com sucesso!\nOK", cadastroPage.getPopupMessageSuccess());
        }
    }

    @Test
    @DisplayName("Testa o cadastro com idade mínima (0 anos)")
    public void testCadastroIdadeMinima() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("Bebê Teste", 0);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();
        Assertions.assertEquals("Sucesso!\nPessoa cadastrada com sucesso!\nOK", cadastroPage.getPopupMessageSuccess());
    }

    @Test
    @DisplayName("Testa o cadastro com idade máxima (122 anos)")
    public void testCadastroIdadeMaxima() {
        cadastroPage.waitForNomeInput();
        cadastroPage.waitForIdadeInput();

        cadastroPage.preencherCadastro("Pessoa Centenária", 122);

        cadastroPage.waitForCadastrarButton();
        cadastroPage.waitForBotaoClicavel(cadastroPage.getCadastrarButton());
        cadastroPage.clicarCadastrar();

        cadastroPage.waitForPopupAndClickOkButton();
        Assertions.assertEquals("Sucesso!\nPessoa cadastrada com sucesso!\nOK", cadastroPage.getPopupMessageSuccess());
    }








//    testes de navegação -> 1- Teste Envio de Formulário
//    Após preencher os campos, clique no botão "Cadastrar" e verifique se o cadastro foi
//    realizado com sucesso (isso pode ser validado com uma nova página, mensagem de
//                           confirmação ou uma lista atualizada de pessoas cadastradas).

}
