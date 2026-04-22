package page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TeamSettingsPage extends BasePage {

    private final By pageTitle = By.xpath("//h4[normalize-space()='Mi Equipo']");
    private final By inviteMemberButton = By.xpath("//button[contains(.,'Invitar a un Miembro')]");
    private final By membersTable = By.cssSelector("table");
    private final By memberRows = By.cssSelector("tbody tr");

    public TeamSettingsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(pageTitle) && isVisible(membersTable);
    }

    public String getTitle() {
        return waitVisible(pageTitle).getText();
    }

    public void clickInviteMember() {
        waitClickable(inviteMemberButton).click();
    }

    public int getMemberCount() {
        return waitVisibleAll(memberRows).size();
    }

    public String getMemberNameByRow(int rowIndex) {
        return getCellText(rowIndex, 1);
    }

    public String getMemberEmailByRow(int rowIndex) {
        return getCellText(rowIndex, 2);
    }

    public String getMemberProjectByRow(int rowIndex) {
        return getCellText(rowIndex, 3);
    }

    public String getMemberAccessByRow(int rowIndex) {
        return getCellText(rowIndex, 4);
    }

    public boolean hasActionsButtonByRow(int rowIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]//button[.//*[contains(@data-testid,'MoreVertIcon')]]");
        return isVisible(locator);
    }

    public void clickActionsByRow(int rowIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]//button[.//*[contains(@data-testid,'MoreVertIcon')]]");
        waitClickable(locator).click();
    }

    public boolean memberExistsByEmail(String email) {
        By locator = By.xpath("//tbody/tr/td[2][normalize-space()='" + email + "']");
        return isVisible(locator);
    }

    public int getRowIndexByEmail(String email) {
        List<org.openqa.selenium.WebElement> rows = waitVisibleAll(memberRows);
        for (int i = 0; i < rows.size(); i++) {
            By emailCell = By.xpath("(//tbody/tr)[" + (i + 1) + "]/td[2]");
            String currentEmail = waitVisible(emailCell).getText().trim();
            if (currentEmail.equals(email)) {
                return i + 1;
            }
        }
        return -1;
    }

    private String getCellText(int rowIndex, int columnIndex) {
        By locator = By.xpath("(//tbody/tr)[" + rowIndex + "]/td[" + columnIndex + "]");
        return waitVisible(locator).getText().trim();
    }
}