package testClass.settings;

import java.time.Duration;
import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.LogInPage;
import page.OrganizationSettingsPage;
import page.TranformPage;

public class OrganizationSettingsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeClass
    public void setup() {
        if (Config.getUserEmail() == null || Config.getUserEmail().isBlank()) {
            throw new IllegalStateException("USER_EMAIL no está configurado.");
        }
        if (Config.getUserPassword() == null || Config.getUserPassword().isBlank()) {
            throw new IllegalStateException("USER_PASSWORD no está configurado.");
        }

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(urlLogin);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));

        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/transform")
        ));

        TranformPage page = new TranformPage(driver);
        page.sidebar().openSettings();
        page.sidebar().goToMyOrganization();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(.,'Configuración del PDF')]")));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV (relacionado con MTX-17).
     * Cobertura adicional sobre la User Story "Configuración de empresa".
     * Detalle: la página "Configuración del PDF" carga correctamente.
     */
    @Test(description = "Sin fila Matriz | Configuracion de empresa carga | US: Configuracion de empresa")
    public void paginaConfiguracionOrganizacionCarga() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "La página 'Configuración del PDF' debe estar visible.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV (relacionado con MTX-17).
     * Cobertura adicional sobre la User Story "Configuración de empresa".
     * Detalle: el campo nombre de la organización está disponible.
     */
    @Test(description = "Sin fila Matriz | Nombre de organizacion disponible | US: Configuracion de empresa",
            dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void nombreOrganizacionEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String name = page.getOrganizationName();
        Assert.assertNotNull(name,
                "El campo nombre de organización debe estar presente.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV (relacionado con MTX-17).
     * Cobertura adicional sobre la User Story "Configuración de empresa".
     * Detalle: el selector de fuente de encabezado está disponible.
     */
    @Test(description = "Sin fila Matriz | Fuente de encabezado disponible | US: Configuracion de empresa",
            dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void fuenteEncabezadoEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String font = page.getHeaderFont();
        Assert.assertNotNull(font,
                "El selector de fuente de encabezado debe estar presente.");
        Assert.assertFalse(font.isBlank(),
                "El selector de fuente de encabezado no debe estar vacío.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV (relacionado con MTX-17).
     * Cobertura adicional sobre la User Story "Configuración de empresa".
     * Detalle: el campo tamaño de letra está disponible.
     */
    @Test(description = "Sin fila Matriz | Tamano de fuente disponible | US: Configuracion de empresa",
            dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void tamanoFuenteEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String fontSize = page.getFontSize();
        Assert.assertNotNull(fontSize,
                "El campo tamaño de fuente debe estar presente.");
        Assert.assertFalse(fontSize.isBlank(),
                "El campo tamaño de fuente no debe estar vacío.");
    }
}
