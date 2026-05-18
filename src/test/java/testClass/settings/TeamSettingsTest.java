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
import page.TeamSettingsPage;
import page.TranformPage;

public class TeamSettingsTest {

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
        page.sidebar().goToMyTeam();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[normalize-space()='Mi Equipo']")));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void paginaEquipoCargaCorrectamente() {
        TeamSettingsPage page = new TeamSettingsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "La página 'Mi Equipo' debe cargar con título y tabla visibles.");
    }

    @Test(dependsOnMethods = "paginaEquipoCargaCorrectamente")
    public void tablaMuestraMiembrosDelEquipo() {
        TeamSettingsPage page = new TeamSettingsPage(driver);
        int count = page.getMemberCount();
        Assert.assertTrue(count > 0,
                "La tabla de equipo debe tener al menos un miembro.");
    }

    @Test(dependsOnMethods = "paginaEquipoCargaCorrectamente")
    public void botonInvitarMiembroEstaVisible() {
        TeamSettingsPage page = new TeamSettingsPage(driver);
        Assert.assertTrue(
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Invitar a un Miembro')]"))) != null,
                "El botón 'Invitar a un Miembro' debe estar visible y clickeable.");
    }

    @Test(dependsOnMethods = "tablaMuestraMiembrosDelEquipo")
    public void primerMiembroTieneEmailVisible() {
        TeamSettingsPage page = new TeamSettingsPage(driver);
        String email = page.getMemberEmailByRow(1);
        Assert.assertNotNull(email, "El email del primer miembro no debe ser nulo.");
        Assert.assertTrue(email.contains("@"),
                "El email del primer miembro debe tener formato de correo válido.");
    }
}
