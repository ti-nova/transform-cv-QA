package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "div[aria-label='Opciones de usuario']")
    private WebElement userMenuContainer;

    @FindBy(xpath = "//li[contains(text(),'Cambiar Contraseña')]")
    private WebElement linkChangePassword;

    @FindBy(id=":r9:")
    private WebElement inputCurrentPassword;

    @FindBy(id=":ra:")
    private WebElement inputNewPassword;

    @FindBy(id=":rb:")
    private WebElement inputConfirmPassword;

    @FindBy(xpath="//button[@type='button' and contains(text(),'Guardar')]")
    private WebElement botonGuardar;

    @FindBy(xpath = "//li[contains(text(),'Cerrar Sesión')]")
    private WebElement opcionCerrarSesion;

    @FindBy(xpath = "//button[@type='button' and contains(text(),'Cerrar Sesión')]")
    private WebElement botonConfirmarLogout;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void openUserMenu() {
        WebElement userMenu = wait.until(
                ExpectedConditions.elementToBeClickable(userMenuContainer)
        );
        userMenu.click();
    }

    public void openChangePasswordModal() {
        wait.until(ExpectedConditions.elementToBeClickable(linkChangePassword)).click();
    }

    public void changePassword(String current, String newPass, String confirm) {
        wait.until(ExpectedConditions.visibilityOf(inputCurrentPassword)).sendKeys(current);
        wait.until(ExpectedConditions.visibilityOf(inputNewPassword)).sendKeys(newPass);
        wait.until(ExpectedConditions.visibilityOf(inputConfirmPassword)).sendKeys(confirm);
        wait.until(ExpectedConditions.elementToBeClickable(botonGuardar)).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(opcionCerrarSesion)).click();
        wait.until(ExpectedConditions.elementToBeClickable(botonConfirmarLogout)).click();
    }
}