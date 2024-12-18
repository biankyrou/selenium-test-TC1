package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class ListaPage {
    private WebDriver driver;

    public ListaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ListaPage(WebDriver driver, WebElement listaPessoas){
        this.driver = driver;
        this.listaPessoas = listaPessoas;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[text()='Remover']")
    private List<WebElement> buttonsRemover;

    @FindBy(xpath = "//*[@id=\"listaPessoas\"]")
    private WebElement listaPessoas;

    @FindBy(xpath = "/html/body/div/a")
    public WebElement voltarParaCadastroButton;

    @FindBy(xpath = "/html/body/div/h1")
    private WebElement tituloLista;

    @FindBy(xpath = "/html/body/div[2]/div/div[6]/button[1]")
    public WebElement confirmarRemover;

    @FindBy(xpath = "/html/body/div[2]/div/div[6]/button[1]")
    public WebElement okClick;

    @FindBy(xpath = "//*[@id=\"editarForm\"]/button")
    public WebElement salvarBotao;

    public boolean isPessoaNaLista(String nome) {
        List<WebElement> listaDePessoas = driver.findElements(By.xpath("//*[@id='listaPessoas']/li"));

        for (WebElement pessoa : listaDePessoas) {
            if (pessoa.getText().contains(nome)) {
                return true;
            }
        }
        return false;
    }

    public void clicarVoltar(){
        voltarParaCadastroButton.click();
    }

    public int encontrarIndicePessoa(String nome) {
        List<WebElement> listaDePessoas = driver.findElements(By.xpath("//*[@id='listaPessoas']/li"));

        for (int i = 0; i < listaDePessoas.size(); i++) {
            WebElement pessoa = listaDePessoas.get(i);

            if (pessoa.getText().contains(nome)) {
                return i + 1;
            }
        }
        return -1;
    }

    public WebElement getBotaoEditar(int indice) {
        return driver.findElement(By.xpath("//*[@id='listaPessoas']/li[" + indice + "]/div/button[1]"));
    }

    public WebElement getBotaoExcluir(int indice) {
        return driver.findElement(By.xpath("//*[@id='listaPessoas']/li[" + indice + "]/div/button[2]"));
    }

    public void clicarBotaoEditar(int indice) {
        WebElement botaoEditar = getBotaoEditar(indice);
        botaoEditar.click();
    }

    public void clicarBotaoExcluir(int indice) {
        WebElement botaoExcluir = getBotaoExcluir(indice);
        botaoExcluir.click();
    }

    public WebElement getListaPessoas() {
        return listaPessoas;
    }
    public String getTituloLista() {
        return tituloLista.getText();
    }

    public List<WebElement> getRemoveButtons() {
        return buttonsRemover;
    }




    //*[@id="listaPessoas"]/li[1]/div/button[1]
    //*[@id="listaPessoas"]/li[2]/div/button[1]

    //*[@id="listaPessoas"]/li[1]

    //*[@id="listaPessoas"]/li[1]/div/button[2]
    //*[@id="listaPessoas"]/li[2]/div/button[2]

}
