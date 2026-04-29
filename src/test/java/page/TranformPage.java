package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TranformPage extends BasePage {

    private final By pageTitle = By.xpath("//h5[contains(.,'Transformar Documento')]");
    private final By hiddenFileInput = By.xpath("//input[@type='file' and contains(@accept,'.pdf,.docx')]");
    private final By uploadArea = By.xpath("//h6[contains(.,'Arrastra y suelta')]");
    private final By requirementsTextArea = By.xpath("//textarea[contains(@placeholder,'Pega aquí los requerimientos')]");
    private final By includePresentationCheckbox = By.xpath("//input[@type='checkbox']");
    private final By transformButton = By.xpath("//button[contains(.,'Subir Archivo')]");
    private final By activeConfigurationTitle = By.xpath("//*[contains(.,'Configuración activa')]");
    private final By selectedFilesText = By.xpath("//p[contains(.,'archivos seleccionados')]");
    private final By alertMessage = By.xpath("//div[@role='alert']//span");

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

        // Algunos componentes React no reaccionan bien si el input está oculto.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.display='block';" +
                        "arguments[0].style.visibility='visible';" +
                        "arguments[0].style.opacity='1';" +
                        "arguments[0].style.height='1px';" +
                        "arguments[0].style.width='1px';",
                input
        );

        input.sendKeys(absoluteFilePath);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                input
        );
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

    public String getSelectedFilesText() {
        return waitVisible(selectedFilesText).getText().trim();
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
}
