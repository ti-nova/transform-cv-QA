package page.components;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.modals.ChangePasswordModal;
import page.modals.UserProfileModal;

public class UserMenuComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By menuTrigger = By.cssSelector("div[aria-label='Opciones de usuario']");
    private final By menuContainer = By.cssSelector("ul[role='menu']");
    private final By myProfileOption = By.xpath("//li[@role='menuitem' and contains(.,'Mi Perfil')]");
    private final By changePasswordOption = By.xpath("//li[@role='menuitem' and contains(.,'Cambiar Contraseña')]");
    private final By logoutOption = By.xpath("//li[@role='menuitem' and contains(.,'Cerrar Sesión')]");

    public UserMenuComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        wait.until(ExpectedConditions.elementToBeClickable(menuTrigger)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuContainer));
    }

    public boolean isOpen() {
        return !driver.findElements(menuContainer).isEmpty();
    }

    public UserProfileModal openProfile() {
        open();
        wait.until(ExpectedConditions.elementToBeClickable(myProfileOption)).click();
        return new UserProfileModal(driver);
    }

    public ChangePasswordModal openChangePassword() {
        open();
        wait.until(ExpectedConditions.elementToBeClickable(changePasswordOption)).click();
        return new ChangePasswordModal(driver);
    }

    public void logout() {
        open();
        wait.until(ExpectedConditions.elementToBeClickable(logoutOption)).click();
    }
}