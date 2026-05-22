package testClass.landing;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LandingPage;
import page.modals.RegistrationModal;

public class LandingPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLanding = "https://tranform-cv.vercel.app/";

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(urlLanding);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='#inicio']")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> Relacionado con MTX-08 (flujo de selección de plan).
     * User Story: Selección de plan y registro de empresa - Automatizada (CSV): No.
     * Detalle: la navegación principal de la landing responde sin redirigir al login.
     */
    @Test(description = "MTX-08 (rel.) | Navegacion de la landing | US: Seleccion de plan y registro de empresa")
    public void landingCargaNavegacionPrincipal() {
        LandingPage landing = new LandingPage(driver);
        landing.clickPlans();
        Assert.assertFalse(driver.getCurrentUrl().contains("/login"),
                "El clic en Planes no debe redirigir al login — debe desplazar la misma página.");
    }

    /**
     * Matriz de Pruebas -> MTX-08
     * Resultado esperado (CSV): "Avanza al flujo de pago si válido"
     * User Story: Selección de plan y registro de empresa - Automatizada (CSV): No.
     * Detalle: seleccionar Plan Básico abre el modal de registro (inicio del flujo).
     */
    @Test(description = "MTX-08 | Seleccion de Plan Basico abre modal de registro | US: Seleccion de plan y registro de empresa")
    public void seleccionPlanBasicoAbreModalRegistro() {
        LandingPage landing = new LandingPage(driver);
        RegistrationModal modal = landing.selectBasicPlan();
        Assert.assertTrue(landing.isRegistrationModalVisible(),
                "Al seleccionar Plan Básico debe abrirse el modal de registro.");
    }

    /**
     * Matriz de Pruebas -> MTX-08
     * Resultado esperado (CSV): "Avanza al flujo de pago si válido"
     * User Story: Selección de plan y registro de empresa - Automatizada (CSV): No.
     * Detalle: seleccionar Plan Avanzado abre el modal de registro.
     */
    @Test(description = "MTX-08 | Seleccion de Plan Avanzado abre modal de registro | US: Seleccion de plan y registro de empresa")
    public void seleccionPlanAvanzadoAbreModalRegistro() {
        LandingPage landing = new LandingPage(driver);
        RegistrationModal modal = landing.selectAdvancedPlan();
        Assert.assertTrue(landing.isRegistrationModalVisible(),
                "Al seleccionar Plan Avanzado debe abrirse el modal de registro.");
    }

    /**
     * Matriz de Pruebas -> MTX-08
     * Resultado esperado (CSV): "Avanza al flujo de pago si válido"
     * User Story: Selección de plan y registro de empresa - Automatizada (CSV): No.
     * Detalle: seleccionar Plan Premium abre el modal de registro.
     */
    @Test(description = "MTX-08 | Seleccion de Plan Premium abre modal de registro | US: Seleccion de plan y registro de empresa")
    public void seleccionPlanPremiumAbreModalRegistro() {
        LandingPage landing = new LandingPage(driver);
        RegistrationModal modal = landing.selectPremiumPlan();
        Assert.assertTrue(landing.isRegistrationModalVisible(),
                "Al seleccionar Plan Premium debe abrirse el modal de registro.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV.
     * Cobertura adicional sobre la User Story "Autenticación de usuario".
     * Detalle: el botón Login del navbar redirige a la página de login.
     */
    @Test(description = "Sin fila Matriz | Boton Login del navbar redirige a /login | US: Autenticacion de usuario")
    public void botonLoginRedirigePaginaLogin() {
        LandingPage landing = new LandingPage(driver);
        landing.clickLogin();
        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "El botón Login del navbar debe redirigir a /login.");
    }
}
