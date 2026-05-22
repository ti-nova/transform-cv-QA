package testClass.processedcvs;

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
import page.ProcessedCvsPage;
import page.TranformPage;

public class ProcessedCvsTest {

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

        new TranformPage(driver).sidebar().goToProcessedCvs();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(.,'CVs Procesados')]")));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV.
     * Cobertura adicional sobre la User Story "Gestión de CV transformados".
     * Detalle: la página de historial carga con título y tabla.
     */
    @Test(description = "Sin fila Matriz | Historial de CVs Procesados carga | US: Gestion de CV transformados")
    public void paginaCvsProcesadosCargaCorrectamente() {
        ProcessedCvsPage page = new ProcessedCvsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "La página 'CVs Procesados' debe cargar con título y tabla visibles.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV.
     * Cobertura adicional sobre la User Story "Gestión de CV transformados".
     * Detalle: la tabla del historial contiene al menos un registro.
     */
    @Test(description = "Sin fila Matriz | Tabla del historial muestra registros | US: Gestion de CV transformados",
            dependsOnMethods = "paginaCvsProcesadosCargaCorrectamente")
    public void tablaMuestraRegistrosDeCV() {
        ProcessedCvsPage page = new ProcessedCvsPage(driver);
        int rows = page.getRowCount();
        Assert.assertTrue(rows > 0,
                "La tabla debe contener al menos un CV procesado.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV.
     * Cobertura adicional sobre la User Story "Gestión de CV transformados".
     * Detalle: la búsqueda por nombre filtra los resultados de la tabla.
     */
    @Test(description = "Sin fila Matriz | Busqueda por nombre filtra resultados | US: Gestion de CV transformados",
            dependsOnMethods = "paginaCvsProcesadosCargaCorrectamente")
    public void busquedaPorNombreFiltraResultados() {
        ProcessedCvsPage page = new ProcessedCvsPage(driver);
        int rowsBefore = page.getRowCount();

        page.clickSearchByName();
        page.searchByName("z");

        wait.until(driver1 -> page.getRowCount() != rowsBefore
                || page.getRowCount() == 0);

        Assert.assertTrue(page.isLoaded(),
                "La página debe seguir mostrando la tabla tras aplicar búsqueda.");
    }

    /**
     * Matriz de Pruebas -> Sin fila directa en el CSV.
     * Cobertura adicional sobre la User Story "Gestión de CV transformados".
     * Detalle: el primer registro de la tabla expone el nombre del candidato.
     */
    @Test(description = "Sin fila Matriz | Primer registro tiene nombre visible | US: Gestion de CV transformados",
            dependsOnMethods = "tablaMuestraRegistrosDeCV")
    public void primerRegistroTieneNombreVisible() {
        ProcessedCvsPage page = new ProcessedCvsPage(driver);
        String name = page.getCandidateNameByRow(1);
        Assert.assertNotNull(name, "El nombre del candidato en la primera fila no debe ser nulo.");
        Assert.assertFalse(name.isBlank(), "El nombre del candidato en la primera fila no debe estar vacío.");
    }
}
