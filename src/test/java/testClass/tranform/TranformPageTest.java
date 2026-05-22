package testClass.tranform;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.Config;
import page.LogInPage;
import page.TranformPage;
import page.modals.ChangePasswordModal;

public class TranformPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";
    private final String urlTranform = "https://tranform-cv.vercel.app/transform";

    private final String originalPassword = Config.getUserPassword();
    private final String temporaryPassword = "ABC12345";

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

        login(Config.getUserEmail(), originalPassword);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/transform")
        ));

        if (driver.getCurrentUrl().contains("/dashboard")) {
            driver.get(urlTranform);
            wait.until(ExpectedConditions.urlContains("/transform"));
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> MTX-13 y MTX-14
     * Resultado esperado (CSV): "Rechaza contrasena invalida" / "Actualiza contrasena y confirma"
     * User Story: Cambio de contrasena - Prioridad: Alta - Automatizada (CSV): Si
     */
    @Test(description = "MTX-13/MTX-14 | Actualiza contrasena y confirma | US: Cambio de contrasena")
    public void cambiarContrasena() {
        TranformPage tranformPage = new TranformPage(driver);

        ChangePasswordModal changePasswordModal = tranformPage
                .userMenu()
                .openChangePassword();

        changePasswordModal.changePassword(
                originalPassword,
                temporaryPassword,
                temporaryPassword
        );

        wait.until(ExpectedConditions.urlContains("/login"));

        login(Config.getUserEmail(), temporaryPassword);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/transform")
        ));

        if (driver.getCurrentUrl().contains("/dashboard")) {
            driver.get(urlTranform);
            wait.until(ExpectedConditions.urlContains("/transform"));
        }

        tranformPage = new TranformPage(driver);

        changePasswordModal = tranformPage
                .userMenu()
                .openChangePassword();

        changePasswordModal.changePassword(
                temporaryPassword,
                originalPassword,
                originalPassword
        );

        wait.until(ExpectedConditions.urlContains("/login"));
    }

    private void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));

        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickIngresar();
    }
}