package testClass.auth;

import java.time.Duration;
import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LogInPage;
import page.TranformPage;

public class CierreSessionTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeMethod
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
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> MTX-15
     * Resultado esperado (CSV): "Cierra sesión y redirige al login"
     * User Story: Cierre de sesión - Prioridad: Alta - Automatizada (CSV): Si
     * Variante: cierre desde el menú de usuario (dropdown superior).
     */
    @Test(description = "MTX-15 | Cierra sesion y redirige al login (menu usuario) | US: Cierre de sesion")
    public void cierraSesionDesdeMenuUsuarioYRedirigAlLogin() {
        new TranformPage(driver).userMenu().logout();

        wait.until(ExpectedConditions.urlContains("/login"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "Después de cerrar sesión debe redirigir al login.");
    }

    /**
     * Matriz de Pruebas -> MTX-15
     * Resultado esperado (CSV): "Cierra sesión y redirige al login"
     * User Story: Cierre de sesión - Prioridad: Alta - Automatizada (CSV): Si
     * Variante: cierre desde la opción del sidebar.
     */
    @Test(description = "MTX-15 | Cierra sesion y redirige al login (sidebar) | US: Cierre de sesion")
    public void cierraSesionDesdeSidebarYRedirigAlLogin() {
        new TranformPage(driver).sidebar().logoutFromSidebar();

        wait.until(ExpectedConditions.urlContains("/login"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "El cierre de sesión desde el sidebar debe redirigir al login.");
    }
}
