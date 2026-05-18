package testClass.settings;

import java.time.Duration;
import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.LogInPage;
import page.OrganizationSettingsPage;
import page.TranformPage;

public class OrganizationSettingsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeTest
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

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void paginaConfiguracionOrganizacionCarga() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "La página 'Configuración del PDF' debe estar visible.");
    }

    @Test(dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void nombreOrganizacionEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String name = page.getOrganizationName();
        Assert.assertNotNull(name,
                "El campo nombre de organización debe estar presente.");
    }

    @Test(dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void fuenteEncabezadoEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String font = page.getHeaderFont();
        Assert.assertNotNull(font,
                "El selector de fuente de encabezado debe estar presente.");
        Assert.assertFalse(font.isBlank(),
                "El selector de fuente de encabezado no debe estar vacío.");
    }

    @Test(dependsOnMethods = "paginaConfiguracionOrganizacionCarga")
    public void tamanoFuenteEstaDisponible() {
        OrganizationSettingsPage page = new OrganizationSettingsPage(driver);
        String fontSize = page.getFontSize();
        Assert.assertNotNull(fontSize,
                "El campo tamaño de fuente debe estar presente.");
        Assert.assertFalse(fontSize.isBlank(),
                "El campo tamaño de fuente no debe estar vacío.");
    }
}
