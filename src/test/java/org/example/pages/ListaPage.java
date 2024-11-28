package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ListaPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"listaPessoas\"]")
    private WebElement listaPessoas;

    @FindBy(xpath = "/html/body/div/a")
    private WebElement voltarParaCadastroButton;

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


    //*[@id="listaPessoas"]/li[1]/div/button[1]
    //*[@id="listaPessoas"]/li[2]/div/button[1]

    //*[@id="listaPessoas"]/li[1]

    //*[@id="listaPessoas"]/li[1]/div/button[2]
    //*[@id="listaPessoas"]/li[2]/div/button[2]




}
