package page;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcessedCvsPage extends BasePage {

    private final By pageTitle = By.xpath("//h4[contains(.,'CVs Procesados')]");
    private final By searchByNameButton = By.xpath("//button[normalize-space()='BUSCAR POR NOMBRE']");
    private final By searchByTagsButton = By.xpath("//button[normalize-space()='BUSCAR POR TAGS']");
    private final By searchInput = By.xpath("//input[@placeholder='Buscar por nombre']");
    private final By table = By.cssSelector("table");
    private final By tableRows = By.cssSelector("tbody tr");
    private final By pagination = By.cssSelector("nav[aria-label='pagination navigation']");
    private final By nextPageButton = By.xpath("//button[@aria-label='Go to next page']");
    private final By currentPageButton = By.xpath("//button[@aria-current='page']");

    public ProcessedCvsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(pageTitle) && isVisible(table);
    }

    public String getTitle() {
        return waitVisible(pageTitle).getText();
    }

    public void clickSearchByName() {
        waitClickable(searchByNameButton).click();
    }

    public void clickSearchByTags() {
        waitClickable(searchByTagsButton).click();
    }

    public void searchByName(String name) {
        WebElement input = waitVisible(searchInput);
        input.clear();
        input.sendKeys(name);
    }

    public int getRowCount() {
        return waitVisibleAll(tableRows).size();
    }

    public boolean isPaginationVisible() {
        return isVisible(pagination);
    }

    public String getCurrentPage() {
        return waitVisible(currentPageButton).getText();
    }

    public void goToNextPage() {
        waitClickable(nextPageButton).click();
    }

    public String getCandidateNameByRow(int rowIndex) {
        return getCellText(rowIndex, 1);
    }

    public String getDateByRow(int rowIndex) {
        return getCellText(rowIndex, 2);
    }

    public String getTransformedByByRow(int rowIndex) {
        return getCellText(rowIndex, 3);
    }

    public void clickPdfButtonByRow(int rowIndex) {
        getActionButton(rowIndex, "PDF").click();
    }

    public void clickDocxButtonByRow(int rowIndex) {
        getActionButton(rowIndex, "DOCX").click();
    }

    public void clickJsonButtonByRow(int rowIndex) {
        getActionButton(rowIndex, "JSON").click();
    }

    public void clickViewPdfButtonByRow(int rowIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]//button[contains(normalize-space(.),'Ver PDF')]");
        waitClickable(locator).click();
    }

    public void clickMoreActionsByRow(int rowIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]//button[.//*[contains(@data-testid,'MoreVertIcon')]]");
        waitClickable(locator).click();
    }

    private String getCellText(int rowIndex, int columnIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]/td[" + columnIndex + "]");
        return waitVisible(locator).getText();
    }

    private WebElement getActionButton(int rowIndex, String label) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]//button[normalize-space(.)='" + label + "' or contains(normalize-space(.),'" + label + "')]");
        return waitClickable(locator);
    }
}