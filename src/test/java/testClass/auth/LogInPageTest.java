package testClass.auth;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import config.DriverFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import config.Config;
import page.LogInPage;

public class LogInPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(urlLogin);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> MTX-11
     * Resultado esperado (CSV): "Rechaza acceso y muestra error"
     * User Story: Autenticacion de usuario - Prioridad: Alta - Automatizada (CSV): Si
     */
    @Test(description = "MTX-11 | Rechaza acceso y muestra error | US: Autenticacion de usuario")
    public void ingresoUsuarioIncorrecto() {
        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail("usuario_invalido@test.com");
        loginPage.enterPassword("clave_invalida");
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    /**
     * Matriz de Pruebas -> MTX-12
     * Resultado esperado (CSV): "Acceso correcto al sistema"
     * User Story: Autenticacion de usuario - Prioridad: Alta - Automatizada (CSV): Si
     */
    @Test(description = "MTX-12 | Acceso correcto al sistema | US: Autenticacion de usuario")
    public void ingresoUsuarioCorrecto() {
        if (Config.getUserEmail() == null || Config.getUserEmail().isBlank()) {
            throw new IllegalStateException("USER_EMAIL no está configurado.");
        }

        if (Config.getUserPassword() == null || Config.getUserPassword().isBlank()) {
            throw new IllegalStateException("USER_PASSWORD no está configurado.");
        }

        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/transform")
        ));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/dashboard")
                        || driver.getCurrentUrl().contains("/transform")
        );
    }
}