package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TranformPage extends BasePage {

    private final By pageTitle = By.xpath("//h5[contains(.,'Transformar Documento')]");
    private final By uploadFileButton = By.xpath("//button[contains(.,'Subir Archivo')]");
    private final By requirementsTextArea = By.xpath("//textarea[contains(@placeholder,'Pega aquí los requerimientos')]");
    private final By includePresentationCheckbox = By.xpath("//input[@type='checkbox']");
    private final By activeConfigurationTitle = By.xpath("//*[contains(.,'Configuración activa')]");
    private final By selectedFilesText = By.xpath("//p[contains(.,'archivos seleccionados')]");

    public TranformPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(pageTitle);
    }

    public String getTitle() {
        return waitVisible(pageTitle).getText();
    }

    public boolean isUploadFileButtonVisible() {
        return isVisible(uploadFileButton);
    }

    public void clickUploadFile() {
        waitClickable(uploadFileButton).click();
    }

    public void enterRequirements(String requirements) {
        waitVisible(requirementsTextArea).clear();
        waitVisible(requirementsTextArea).sendKeys(requirements);
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

    public boolean isActiveConfigurationVisible() {
        return isVisible(activeConfigurationTitle);
    }

    public String getSelectedFilesText() {
        return waitVisible(selectedFilesText).getText();
    }
}