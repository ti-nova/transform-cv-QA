package page.modals;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProfileModal {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dialogTitle = By.xpath("//h6[normalize-space()='Perfil de Usuario']");
    private final By firstName = By.xpath("//p[.//strong[contains(.,'Nombre:')]]");
    private final By lastName = By.xpath("//p[.//strong[contains(.,'Apellido:')]]");
    private final By role = By.xpath("//p[.//strong[contains(.,'Rol:')]]");
    private final By email = By.xpath("//p[.//strong[contains(.,'Correo:')]]");
    private final By changePasswordButton = By.xpath("//button[normalize-space()='Cambiar Contraseña']");
    private final By closeButton = By.xpath("//button[normalize-space()='Cerrar']");

    public UserProfileModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOpen() {
        return !driver.findElements(dialogTitle).isEmpty();
    }

    public String getFirstName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).getText();
    }

    public String getLastName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).getText();
    }

    public String getRole() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(role)).getText();
    }

    public String getEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(email)).getText();
    }

    public ChangePasswordModal clickChangePassword() {
        wait.until(ExpectedConditions.elementToBeClickable(changePasswordButton)).click();
        return new ChangePasswordModal(driver);
    }

    public void close() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}