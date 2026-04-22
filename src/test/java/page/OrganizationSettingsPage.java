package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrganizationSettingsPage extends BasePage {

    private final By pageTitle = By.xpath("//h4[contains(.,'Configuración del PDF')]");
    private final By organizationNameInput = By.xpath("//label[contains(.,'Nombre de la Organización')]/following-sibling::*//input");
    private final By logoFileInput = By.xpath("//input[@type='file']");
    private final By logoFileName = By.xpath("//p[contains(.,'.png') or contains(.,'.jpg') or contains(.,'.jpeg') or contains(.,'.svg')]");
    private final By removeLogoButton = By.xpath("//button[.//*[contains(@data-testid,'CloseIcon')]]");

    private final By headerFontSelect = By.xpath("//label[contains(.,'Fuente Encabezado')]/following-sibling::*//div[@role='combobox']");
    private final By paragraphFontSelect = By.xpath("//label[contains(.,'Fuente Párrafo')]/following-sibling::*//div[@role='combobox']");

    private final By headerColorInput = By.xpath("//label[contains(.,'Color Encabezado')]/following-sibling::*//input[@type='color']");
    private final By paragraphColorInput = By.xpath("//label[contains(.,'Color Párrafo')]/following-sibling::*//input[@type='color']");
    private final By fontSizeInput = By.xpath("//label[contains(.,'Tamaño de letra')]/following-sibling::*//input[@type='number']");

    private final By saveChangesButton = By.xpath("//button[contains(.,'Guardar Cambios')]");

    public OrganizationSettingsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(pageTitle);
    }

    public String getTitle() {
        return waitVisible(pageTitle).getText();
    }

    public String getOrganizationName() {
        return waitVisible(organizationNameInput).getAttribute("value");
    }

    public void setOrganizationName(String organizationName) {
        WebElement input = waitVisible(organizationNameInput);
        input.clear();
        input.sendKeys(organizationName);
    }

    public String getLogoFileName() {
        return waitVisible(logoFileName).getText();
    }

    public void uploadLogo(String absoluteFilePath) {
        waitVisible(logoFileInput).sendKeys(absoluteFilePath);
    }

    public void removeLogo() {
        waitClickable(removeLogoButton).click();
    }

    public String getHeaderFont() {
        return waitVisible(headerFontSelect).getText();
    }

    public void selectHeaderFont(String fontName) {
        selectDropdownOption(headerFontSelect, fontName);
    }

    public String getParagraphFont() {
        return waitVisible(paragraphFontSelect).getText();
    }

    public void selectParagraphFont(String fontName) {
        selectDropdownOption(paragraphFontSelect, fontName);
    }

    public String getHeaderColor() {
        return waitVisible(headerColorInput).getAttribute("value");
    }

    public void setHeaderColor(String colorHex) {
        setInputValue(headerColorInput, colorHex);
    }

    public String getParagraphColor() {
        return waitVisible(paragraphColorInput).getAttribute("value");
    }

    public void setParagraphColor(String colorHex) {
        setInputValue(paragraphColorInput, colorHex);
    }

    public String getFontSize() {
        return waitVisible(fontSizeInput).getAttribute("value");
    }

    public void setFontSize(String fontSize) {
        WebElement input = waitVisible(fontSizeInput);
        input.clear();
        input.sendKeys(fontSize);
    }

    public void clickSaveChanges() {
        waitClickable(saveChangesButton).click();
    }

    public void configurePdf(String organizationName, String headerFont, String paragraphFont,
                             String headerColor, String paragraphColor, String fontSize) {
        setOrganizationName(organizationName);
        selectHeaderFont(headerFont);
        selectParagraphFont(paragraphFont);
        setHeaderColor(headerColor);
        setParagraphColor(paragraphColor);
        setFontSize(fontSize);
    }

    private void selectDropdownOption(By dropdownLocator, String optionText) {
        waitClickable(dropdownLocator).click();
        By optionLocator = By.xpath("//ul[@role='listbox']//li[normalize-space()='" + optionText + "']");
        waitClickable(optionLocator).click();
    }

    private void setInputValue(By locator, String value) {
        WebElement input = waitVisible(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                input,
                value
        );
    }
}