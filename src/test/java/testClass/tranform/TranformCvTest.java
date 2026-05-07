package testClass.tranform;

import java.nio.file.Files;
import java.nio.file.Path;
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

import config.Config;
import page.LogInPage;
import page.TranformPage;

public class TranformCvTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";
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

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();
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

    @Test
    public void cpTr001_cargaPdfValido_registraCargaEnUi() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath = getFilePath("cv_valido.pdf");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del PDF. El contador siguió igual: " + initialCounter
        );
    }

    @Test
    public void cpTr002_cargaDocxValido_registraCargaEnUi() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath = getFilePath("cv_valido.docx");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del DOCX. El contador siguió igual: " + initialCounter
        );
    }

    @Test
    public void cpTr003_transformarSinArchivo_muestraAlerta() {
        TranformPage tranformPage = new TranformPage(driver);

        tranformPage.clickTransformButton();

        Assert.assertTrue(
                tranformPage.hasNoFileSelectedAlert(),
                "Debería mostrarse la alerta de que falta seleccionar un archivo."
        );
    }

    @Test
    public void cpTr004_ingresoRequerimientos_actualizaTextarea() {
        TranformPage tranformPage = new TranformPage(driver);
        String requirements = "QA Analyst con experiencia en Selenium, TestNG y pruebas funcionales.";

        tranformPage.enterRequirements(requirements);

        Assert.assertEquals(tranformPage.getRequirementsText(), requirements);
    }

    @Test
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

    @Test
    public void cpTr006_archivoInvalido_mantieneEstadoSinCargaValida() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath = getFilePath("archivo_invalido.txt");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);
        tranformPage.clickTransformButton();

        boolean counterChanged = tranformPage.waitUntilSelectedFilesTextChanges(initialCounter);

        Assert.assertTrue(
                !counterChanged || tranformPage.hasNoFileSelectedAlert(),
                "Un archivo inválido no debería comportarse como una carga válida transformable."
        );
    }

    @Test
    public void cpTr007_transformarPdfValido_requiereCargaRegistradaAntesDeProcesar() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath = getFilePath("cv_valido.pdf");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del PDF, por lo que no tiene sentido continuar con la transformación."
        );

        tranformPage.enterRequirements("QA Analyst con experiencia en Selenium y automatización.");
        tranformPage.clickTransformButton();

        Assert.assertFalse(
                tranformPage.hasNoFileSelectedAlert(),
                "Después de cargar un PDF válido, no debería seguir apareciendo la alerta de 'selecciona al menos un archivo'."
        );
    }

    @Test
    public void cpTr008_transformarDocxValido_requiereCargaRegistradaAntesDeProcesar() {
        TranformPage tranformPage = new TranformPage(driver);
        String filePath = getFilePath("cv_valido.docx");
        String initialCounter = tranformPage.getSelectedFilesText();

        tranformPage.uploadCv(filePath);

        Assert.assertTrue(
                tranformPage.waitUntilSelectedFilesTextChanges(initialCounter),
                "La UI no registró la carga del DOCX, por lo que no tiene sentido continuar con la transformación."
        );

        tranformPage.enterRequirements("QA Analyst con experiencia en Selenium y automatización.");
        tranformPage.clickTransformButton();

        Assert.assertFalse(
                tranformPage.hasNoFileSelectedAlert(),
                "Después de cargar un DOCX válido, no debería seguir apareciendo la alerta de 'selecciona al menos un archivo'."
        );
    }

    private void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));

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
