package testClass.dashboard;

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
import page.DashboardPage;
import page.LogInPage;
import page.TranformPage;

public class DashboardTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";
    private final String urlDashboard = "https://tranform-cv.vercel.app/dashboard";

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

        if (!driver.getCurrentUrl().contains("/dashboard")) {
            new TranformPage(driver).sidebar().goToDashboard();
            wait.until(ExpectedConditions.urlContains("/dashboard"));
        }

        // Espera a que el contenido del dashboard renderice (no solo la URL),
        // para que los tests no evalúen la página antes de tiempo.
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(.,'Consumo de CVs Transformados')]")));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> MTX-16
     * Resultado esperado (CSV): "Métricas correctas desplegadas"
     * User Story: Consultar métricas por el listado de clientes - Prioridad: Media
     * Automatizada (CSV): la fila figura como "No aplica" (manual); este test la automatiza.
     */
    @Test(description = "MTX-16 | Metricas correctas desplegadas - titulo del panel | US: Consultar metricas")
    public void dashboardCargaTituloConsumoDeCV() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isLoaded(),
                "El título 'Consumo de CVs Transformados' debe estar visible en el dashboard.");
    }

    /**
     * Matriz de Pruebas -> MTX-16
     * Resultado esperado (CSV): "Métricas correctas desplegadas"
     * User Story: Consultar métricas por el listado de clientes
     */
    @Test(description = "MTX-16 | Metricas correctas desplegadas - CVs usados | US: Consultar metricas",
            dependsOnMethods = "dashboardCargaTituloConsumoDeCV")
    public void dashboardMuestraMetricaCvsUsados() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isCvsUsedVisible(),
                "La etiqueta 'CVs usados' debe ser visible.");
    }

    /**
     * Matriz de Pruebas -> MTX-16
     * Resultado esperado (CSV): "Métricas correctas desplegadas"
     * User Story: Consultar métricas por el listado de clientes
     */
    @Test(description = "MTX-16 | Metricas correctas desplegadas - CVs restantes | US: Consultar metricas",
            dependsOnMethods = "dashboardCargaTituloConsumoDeCV")
    public void dashboardMuestraMetricaCvsRestantes() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isCvsRemainingVisible(),
                "La etiqueta 'CVs restantes' debe ser visible.");
    }

    /**
     * Matriz de Pruebas -> MTX-16
     * Resultado esperado (CSV): "Métricas correctas desplegadas"
     * User Story: Consultar métricas por el listado de clientes
     */
    @Test(description = "MTX-16 | Metricas correctas desplegadas - grafico por usuario | US: Consultar metricas",
            dependsOnMethods = "dashboardCargaTituloConsumoDeCV")
    public void dashboardMuestraGraficoUtilizacionPorUsuario() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isUsageByUserChartVisible(),
                "El gráfico 'UTILIZACIÓN POR USUARIO' debe ser visible.");
    }
}
