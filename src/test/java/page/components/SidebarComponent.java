package page.components;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.DashboardPage;

public class SidebarComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardOption = By.xpath("//span[normalize-space()='Dashboard']");
    private final By transformDocumentOption = By.xpath("//span[normalize-space()='Transformar Documento']");
    private final By processedCvsOption = By.xpath("//span[normalize-space()='CVs Procesados']");
    private final By settingsOption = By.xpath("//span[normalize-space()='Ajustes']");
    private final By myOrganizationOption = By.xpath("//span[normalize-space()='Mi Organización']");
    private final By myTeamOption = By.xpath("//span[normalize-space()='Mi Equipo']");
    private final By rolesPermissionsOption = By.xpath("//span[normalize-space()='Roles y Permisos']");
    private final By logoutOption = By.xpath("//span[normalize-space()='Cerrar sesión']");
    // El botón de confirmar (acción destructiva, color rojo) en el diálogo
    // "¿Cerrar sesión?". Se localiza por la clase de color para tolerar las
    // variaciones de texto ("Cerrar Sesión" / "Cerrar sesión").
    private final By confirmLogoutButton = By.xpath("//div[@role='dialog']//button[contains(@class,'MuiButton-colorError')]");

    public SidebarComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public DashboardPage goToDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(dashboardOption)).click();
        return new DashboardPage(driver);
    }

    public void goToTransformDocument() {
        wait.until(ExpectedConditions.elementToBeClickable(transformDocumentOption)).click();
    }

    public void goToProcessedCvs() {
        wait.until(ExpectedConditions.elementToBeClickable(processedCvsOption)).click();
    }

    public void openSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(settingsOption)).click();
    }

    public void goToMyOrganization() {
        wait.until(ExpectedConditions.elementToBeClickable(myOrganizationOption)).click();
    }

    public void goToMyTeam() {
        wait.until(ExpectedConditions.elementToBeClickable(myTeamOption)).click();
    }

    public void goToRolesAndPermissions() {
        wait.until(ExpectedConditions.elementToBeClickable(rolesPermissionsOption)).click();
    }

    public void logoutFromSidebar() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutOption)).click();
        // Aparece el diálogo de confirmación "¿Cerrar sesión?".
        wait.until(ExpectedConditions.elementToBeClickable(confirmLogoutButton)).click();
    }
}
