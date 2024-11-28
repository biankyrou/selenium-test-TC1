package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CadastroPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id='nome']")
    private WebElement nomeInput;

    @FindBy(xpath = "//*[@id=\"idade\"]")
    private WebElement idadeInput;

    @FindBy(xpath = "//*[@id=\"cadastroForm\"]/button")
    private WebElement cadastrarButton;

    @FindBy(xpath = "/html/body/div/a")
    private WebElement visualizarButton;

    public CadastroPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isNomeInputVisible() {
        try {
            return nomeInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isIdadeInputVisible() {
        try {
            return idadeInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCadastrarButtonVisible() {
        try {
            return cadastrarButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisualizarButtonVisible() {
        try {
            return visualizarButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void preencherNome(String nome){
        nomeInput.clear();
        nomeInput.sendKeys(nome);
    }

    public void preencherIdade(Integer idade){
        idadeInput.clear();
        idadeInput.sendKeys(idade.toString());
    }

    public void preencherCadastro(String nome, Integer idade){
        nomeInput.clear();
        idadeInput.clear();
        nomeInput.sendKeys(nome);
        idadeInput.sendKeys(idade.toString());
    }

    public void clicarCadastrar(){
        cadastrarButton.click();
    }

    public void clicarVisualizarPessoas(){
        visualizarButton.click();
    }

    public String getNomeValue() {
        return nomeInput.getAttribute("value");
    }

    public Integer getIdadeValue() {
        String valor = idadeInput.getAttribute("value");
        return Integer.parseInt(valor);  // Converte a String para Integer
    }

}
