package page;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TranformPage extends BasePage {

    private final By pageTitle        = By.xpath("//h5[contains(.,'Transformar Documento')]");
    private final By hiddenFileInput  = By.xpath("//input[@type='file' and contains(@accept,'.pdf,.docx')]");
    private final By uploadArea       = By.xpath("//h6[contains(.,'Arrastra y suelta')]");
    private final By requirementsTextArea = By.xpath("//textarea[contains(@placeholder,'Pega aquí los requerimientos')]");
    private final By includePresentationCheckbox = By.xpath("//input[@type='checkbox']");
    private final By transformButton  = By.xpath("//button[contains(.,'Subir Archivo')]");
    private final By activeConfigurationTitle = By.xpath("//*[contains(.,'Configuración activa')]");
    private final By selectedFilesText = By.xpath("//p[contains(.,'archivos seleccionados')]");
    private final By alertMessage     = By.xpath("//div[@role='alert']//span");

    // Cada archivo cargado genera un <p class="css-1fk7th5"> con su nombre en la lista
    private final By fileItemNames    = By.xpath("//p[contains(@class,'css-1fk7th5')]");

    // Alerta de éxito que aparece tras transformar: "✅ 1 archivo(s) procesado(s) con éxito."
    private final By successAlert     = By.xpath("//div[@role='alert']//span[contains(.,'procesado')]");

    // Enlace de descarga que aparece bajo la alerta de éxito
    private final By downloadLink     = By.xpath("//a[contains(.,'Ver procesado')]");

    public TranformPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(pageTitle);
    }

    public String getTitle() {
        return waitVisible(pageTitle).getText();
    }

    public boolean isUploadAreaVisible() {
        return isVisible(uploadArea);
    }

    public void uploadCv(String absoluteFilePath) {
        WebElement input = waitPresent(hiddenFileInput);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.display='block';" +
                        "arguments[0].style.visibility='visible';" +
                        "arguments[0].style.opacity='1';" +
                        "arguments[0].style.height='1px';" +
                        "arguments[0].style.width='1px';",
                input
        );

        input.sendKeys(absoluteFilePath);

        // React necesita el evento 'change' para actualizar su estado interno.
        // Solo 'change' (no 'input') para evitar el doble registro.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                input
        );
    }

    /**
     * Devuelve cuántos archivos están registrados visualmente en la lista de la UI.
     * Útil para detectar doble upload: debería ser exactamente 1 tras subir un archivo.
     */
    public int getUploadedFileCount() {
        List<WebElement> items = driver.findElements(fileItemNames);
        return items.size();
    }

    public String getUploadedFileValue() {
        return waitPresent(hiddenFileInput).getAttribute("value");
    }

    public boolean waitUntilSelectedFilesTextChanges(String previousValue) {
        try {
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.textToBe(selectedFilesText, previousValue)
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getSelectedFilesText() {
        return waitVisible(selectedFilesText).getText().trim();
    }

    public void enterRequirements(String requirements) {
        WebElement textArea = waitVisible(requirementsTextArea);
        textArea.clear();
        textArea.sendKeys(requirements);
    }

    public String getRequirementsText() {
        return waitVisible(requirementsTextArea).getAttribute("value");
    }

    public boolean isIncludePresentationChecked() {
        return waitVisible(includePresentationCheckbox).isSelected();
    }

    public void clickIncludePresentationCheckbox() {
        waitClickable(includePresentationCheckbox).click();
    }

    public boolean isTransformButtonVisible() {
        return isVisible(transformButton);
    }

    public boolean isTransformButtonEnabled() {
        return waitVisible(transformButton).isEnabled();
    }

    public void clickTransformButton() {
        waitClickable(transformButton).click();
    }

    public boolean isActiveConfigurationVisible() {
        return isVisible(activeConfigurationTitle);
    }

    public boolean isAlertMessageVisible() {
        return isVisible(alertMessage);
    }

    public String getAlertMessage() {
        return waitVisible(alertMessage).getText().trim();
    }

    public boolean hasNoFileSelectedAlert() {
        return isAlertMessageVisible()
                && getAlertMessage().toLowerCase().contains("selecciona al menos un archivo");
    }

    /**
     * Espera hasta 60s a que aparezca la alerta "✅ X archivo(s) procesado(s) con éxito."
     * y el enlace de descarga "📂 Ver procesado ...".
     * Ambos elementos aparecen juntos cuando el servidor termina la transformación.
     */
    public boolean waitUntilTransformationCompletes() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            longWait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOfElementLocated(successAlert),
                    ExpectedConditions.visibilityOfElementLocated(downloadLink)
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Devuelve el texto de la alerta de éxito, ej: "✅ 1 archivo(s) procesado(s) con éxito."
     * Útil para verificar el mensaje exacto en el assert.
     */
    public String getSuccessAlertText() {
        return waitVisible(successAlert).getText().trim();
    }

    /**
     * Devuelve la URL del enlace de descarga del CV procesado.
     */
    public String getDownloadLinkUrl() {
        return waitVisible(downloadLink).getAttribute("href");
    }
}