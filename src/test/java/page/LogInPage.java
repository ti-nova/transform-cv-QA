package page;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInPage {

    private final WebDriverWait wait;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement inputUser;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement botonIngresar;

    public LogInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUserEmail(String user) {
        inputUser.clear();
        inputUser.sendKeys(user);
    }

    public void enterPassword(String password) {
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void clickIngresar() {
        // Espera a que el botón esté clickeable antes de hacer submit
        // React puede deshabilitarlo brevemente mientras valida el formulario
        wait.until(ExpectedConditions.elementToBeClickable(botonIngresar)).click();
    }
}