package page.modals;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordModal {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dialogTitle = By.xpath("//h2[contains(.,'Cambiar Contraseña')]");
    private final By currentPasswordInput = By.xpath("//label[contains(.,'Contraseña actual')]/following-sibling::*//input");
    private final By newPasswordInput = By.xpath("//label[contains(.,'Nueva contraseña')]/following-sibling::*//input");
    private final By confirmPasswordInput = By.xpath("//label[contains(.,'Confirmar nueva contraseña')]/following-sibling::*//input");
    private final By cancelButton = By.xpath("//button[normalize-space()='Cancelar']");
    private final By saveButton = By.xpath("//button[normalize-space()='Guardar']");

    public ChangePasswordModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOpen() {
        return !driver.findElements(dialogTitle).isEmpty();
    }

    public void fillCurrentPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(currentPasswordInput)).sendKeys(password);
    }

    public void fillNewPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newPasswordInput)).sendKeys(password);
    }

    public void fillConfirmPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)).sendKeys(password);
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        fillCurrentPassword(currentPassword);
        fillNewPassword(newPassword);
        fillConfirmPassword(confirmPassword);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void waitUntilClosed() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogTitle));
    }

    public void cancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        waitUntilClosed();
    }
}