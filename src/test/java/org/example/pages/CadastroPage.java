package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CadastroPage {
    private WebDriver driver;

    @FindBy(name = "nome")
    private WebElement nomeInput;

    @FindBy(name = "idade")
    private WebElement idadeInput;

    @FindBy(xpath = "//button[text()='Cadastrar']")
    private WebElement cadastrarButton;

    @FindBy(xpath = "//button[text()='Visualizar Pessoas Cadastradas']")
    private WebElement visualizarButton;

    public CadastroPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void preencherNome(String nome){
        nomeInput.clear();
        nomeInput.sendKeys(nome);
    }

    public void preencherIdade(String idade){
        idadeInput.clear();
        idadeInput.sendKeys(idade);
    }

    public void clicarCadastrar(){
        cadastrarButton.click();
    }

    public void clicarVisualizarPessoas(){
        visualizarButton.click();
    }
}
