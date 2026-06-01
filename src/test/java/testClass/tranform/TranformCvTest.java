package testClass.tranform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import config.DriverFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import config.Config;
import page.LogInPage;
import page.ProcessedCvsPage;
import page.TranformPage;

public class TranformCvTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin    = "https://tranform-cv.vercel.app/login";
    private final String urlTranform = "https://tranform-cv.vercel.app/transform";
    private final Path testDataPath = Path.of(
            System.getProperty("user.dir"),
            "src", "test", "resources", "testdata", "transform"
    );

    @BeforeMethod
    public void setup() {
        if (Config.getUserEmail() == null || Config.getUserEmail().isBlank()) {
            throw new IllegalStateException("USER_EMAIL no está configurado.");
        }
        if (Config.getUserPassword() == null || Config.getUserPassword().isBlank()) {
            throw new IllegalStateException("USER_PASSWORD no está configurado.");
        }

        ChromeOptions options = new ChromeOptions();
        // Mantiene visibles los confirm() nativos del navegador para poder
        // aceptarlos explícitamente (la app muestra "carga en curso" al salir).
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        driver = DriverFactory.create(options);
        wait   = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(urlLogin);

        login(Config.getUserEmail(), Config.getUserPassword());

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/transform")
        ));

        if (driver.getCurrentUrl().contains("/dashboard")) {
            driver.get(urlTranform);
            wait.until(ExpectedConditions.urlContains("/transform"));
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: carga de PDF valido - la UI debe registrar exactamente 1 archivo.
     * Automatizada (CSV): la fila MTX-18 figura como "No"; este test la automatiza.
     */
    @Test(description = "MTX-18 (Transformar CV) | Carga de PDF valido registra 1 archivo en la UI")
    public void cpTr001_cargaPdfValido_registraCargaEnUi() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath       = getFilePath("cv_valido.pdf");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del PDF. El contador siguió igual: " + initialCounter
        );

        // Verifica que solo se registró 1 archivo — detecta doble upload
        Assert.assertEquals(
                tranformPage.getUploadedFileCount(), 1,
                "Se esperaba 1 archivo en la lista pero se encontraron: " + tranformPage.getUploadedFileCount()
        );
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: carga de DOCX valido - la UI debe registrar exactamente 1 archivo.
     */
    @Test(description = "MTX-18 (Transformar CV) | Carga de DOCX valido registra 1 archivo en la UI")
    public void cpTr002_cargaDocxValido_registraCargaEnUi() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath       = getFilePath("cv_valido.docx");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del DOCX. El contador siguió igual: " + initialCounter
        );

        // Verifica que solo se registró 1 archivo — detecta doble upload
        Assert.assertEquals(
                tranformPage.getUploadedFileCount(), 1,
                "Se esperaba 1 archivo en la lista pero se encontraron: " + tranformPage.getUploadedFileCount()
        );
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: intentar transformar sin archivo seleccionado debe mostrar alerta.
     */
    @Test(description = "MTX-18 (Transformar CV) | Transformar sin archivo muestra alerta")
    public void cpTr003_transformarSinArchivo_muestraAlerta() {
        TranformPage tranformPage = new TranformPage(driver);

        tranformPage.clickTransformButton();

        Assert.assertTrue(
                tranformPage.hasNoFileSelectedAlert(),
                "Debería mostrarse la alerta de que falta seleccionar un archivo."
        );
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: el textarea de requerimientos refleja el texto ingresado.
     */
    @Test(description = "MTX-18 (Transformar CV) | Ingreso de requerimientos actualiza el textarea")
    public void cpTr004_ingresoRequerimientos_actualizaTextarea() {
        TranformPage tranformPage = new TranformPage(driver);
        String requirements = "QA Analyst con experiencia en Selenium, TestNG y pruebas funcionales.";

        tranformPage.enterRequirements(requirements);

        Assert.assertEquals(tranformPage.getRequirementsText(), requirements);
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: el checkbox "Incluir presentacion" cambia de estado al hacer clic.
     */
    @Test(description = "MTX-18 (Transformar CV) | Checkbox de presentacion cambia de estado")
    public void cpTr005_checkboxPresentacion_cambiaEstado() {
        TranformPage tranformPage = new TranformPage(driver);
        boolean initialState = tranformPage.isIncludePresentationChecked();

        tranformPage.clickIncludePresentationCheckbox();

        Assert.assertNotEquals(
                tranformPage.isIncludePresentationChecked(),
                initialState,
                "El checkbox debería cambiar de estado al hacer click."
        );
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: relacionado con "Solo deja cargar archivos en PDF" - un archivo
     * .txt no debe comportarse como una carga valida transformable.
     */
    @Test(description = "MTX-18 (Transformar CV) | Archivo invalido (.txt) no se procesa como carga valida")
    public void cpTr006_archivoInvalido_mantieneEstadoSinCargaValida() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath       = getFilePath("archivo_invalido.txt");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        // Un archivo inválido debe ser rechazado: la app abre un diálogo de
        // error, o bien el contador de archivos seleccionados no cambia.
        boolean dialogoDeError = tranformPage.waitForModalDialog(5);
        boolean counterChanged = tranformPage.waitUntilSelectedFilesTextChanges(initialCounter);

        Assert.assertTrue(
                dialogoDeError || !counterChanged,
                "Un archivo inválido (.txt) no debería comportarse como una carga válida transformable."
        );
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: flujo completo - transformar un PDF valido y verificar que el
     * registro queda persistido en el historial de CVs Procesados.
     */
    @Test(description = "MTX-18 (Transformar CV) | Transformar PDF valido completa con exito y queda en historial")
    public void cpTr007_transformarPdfValido_completaTransformacionConExito() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath       = getFilePath("cv_valido.pdf");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del PDF."
        );

        Assert.assertEquals(
                tranformPage.getUploadedFileCount(), 1,
                "Se detectó doble upload del PDF: " + tranformPage.getUploadedFileCount() + " archivos en lista."
        );

        tranformPage.enterRequirements("QA Analyst con experiencia en Selenium y automatización.");
        tranformPage.clickTransformButton();

        Assert.assertTrue(
                tranformPage.waitUntilTransformationCompletes(),
                "La transformación del PDF no completó en 120s: no apareció la alerta de éxito ni el enlace de descarga."
        );

        Assert.assertTrue(
                tranformPage.getSuccessAlertText().contains("procesado"),
                "El texto de la alerta de éxito no es el esperado: " + tranformPage.getSuccessAlertText()
        );

        Assert.assertTrue(
                tranformPage.getDownloadLinkUrl().contains("cv_valido"),
                "El enlace de descarga no contiene el nombre del archivo original: " + tranformPage.getDownloadLinkUrl()
        );

        // Navega al historial y confirma que el backend persistió el registro
        // antes de que @AfterMethod cierre el browser.
        verificarRegistroEnHistorial(tranformPage);
    }

    /**
     * Matriz de Pruebas -> MTX-18 (User Story: Transformar CV)
     * Detalle: flujo completo - transformar un DOCX valido y verificar que el
     * registro queda persistido en el historial de CVs Procesados.
     */
    @Test(description = "MTX-18 (Transformar CV) | Transformar DOCX valido completa con exito y queda en historial")
    public void cpTr008_transformarDocxValido_completaTransformacionConExito() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath       = getFilePath("cv_valido.docx");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del DOCX."
        );

        Assert.assertEquals(
                tranformPage.getUploadedFileCount(), 1,
                "Se detectó doble upload del DOCX: " + tranformPage.getUploadedFileCount() + " archivos en lista."
        );

        tranformPage.enterRequirements("QA Analyst con experiencia en Selenium y automatización.");
        tranformPage.clickTransformButton();

        Assert.assertTrue(
                tranformPage.waitUntilTransformationCompletes(),
                "La transformación del DOCX no completó en 120s: no apareció la alerta de éxito ni el enlace de descarga."
        );

        Assert.assertTrue(
                tranformPage.getSuccessAlertText().contains("procesado"),
                "El texto de la alerta de éxito no es el esperado: " + tranformPage.getSuccessAlertText()
        );

        Assert.assertTrue(
                tranformPage.getDownloadLinkUrl().contains("cv_valido"),
                "El enlace de descarga no contiene el nombre del archivo original: " + tranformPage.getDownloadLinkUrl()
        );

        // Navega al historial y confirma que el backend persistió el registro
        // antes de que @AfterMethod cierre el browser.
        verificarRegistroEnHistorial(tranformPage);
    }

    private void verificarRegistroEnHistorial(TranformPage tranformPage) {
        tranformPage.sidebar().goToProcessedCvs();

        // Al navegar fuera, la app puede mostrar un confirm() nativo
        // ("⚠️ Tienes una carga de CVs en curso..."). Lo aceptamos para que la
        // navegación al historial proceda.
        aceptarConfirmSiAparece();

        // Espera a que el historial renderice: título y al menos una fila.
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(.,'CVs Procesados')]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("tbody tr")));

        ProcessedCvsPage historial = new ProcessedCvsPage(driver);
        Assert.assertTrue(historial.countRowsNow() > 0,
                "El CV transformado no aparece en el historial — el backend posiblemente no persistió el registro.");
    }

    /**
     * Acepta el confirm() nativo del navegador si aparece dentro de 5s.
     * Si no aparece, la navegación ya procedió y no se hace nada.
     */
    private void aceptarConfirmSiAparece() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
            // No apareció ningún confirm; la navegación procedió directamente.
        }
    }

    private void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));

        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickIngresar();
    }

    private String getFilePath(String fileName) {
        Path filePath = testDataPath.resolve(fileName).toAbsolutePath();
        Assert.assertTrue(Files.exists(filePath), "No existe el archivo de prueba: " + filePath);
        return filePath.toString();
    }
}