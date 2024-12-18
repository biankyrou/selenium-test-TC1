package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @FindBy(xpath = "/html/body/div[2]/div")
    private WebElement popup;

    @FindBy(xpath = "//*[@id=\"swal2-title\"]")
    private WebElement successMessagePopUp;

    @FindBy(xpath = "/html/body/div[2]/div/div[6]/button[1]")
    private WebElement okPopUpButton;

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

    public Integer getIdadeString() {
        String valor = idadeInput.getAttribute("value");
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public WebElement getCadastrarButton() {
        return cadastrarButton;
    }

    public String getPopupMessageSuccess() {
        return popup.getText();
    }

    private void waitForElement(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.err.println("Elemento não encontrado: " + element);
            throw e;
        }
    }

    public void waitForNomeInput() {
        waitForElement(nomeInput);
    }

    public void waitForIdadeInput() {
        waitForElement(idadeInput);
    }

    public void waitForCadastrarButton() {
        waitForElement(cadastrarButton);
    }

    public void waitForVisualizarButton() {
        waitForElement(visualizarButton);
    }

    public WebElement waitForBotaoClicavel(WebElement botaoLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(botaoLocator));
    }

    public void waitForPopupAndClickOkButton() {
        waitForElement(popup);
        waitForBotaoClicavel(okPopUpButton);
        okPopUpButton.click();
    }

    public void colarIdade(String valor) {
        idadeInput.click();
        idadeInput.sendKeys(valor);
    }

    public void colarNome(String valor) {
        nomeInput.click();
        nomeInput.sendKeys(valor);
    }

}
