package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By title = By.xpath("//h4[contains(.,'Consumo de CVs Transformados')]");
    private final By premiumPlanBadge = By.xpath("//span[contains(.,'Plan Premium')]");
    private final By monthlyProgressText = By.xpath("//h6[contains(.,'Progreso mensual:')]");
    private final By cvsUsedLabel = By.xpath("//*[contains(.,'CVs usados')]");
    private final By cvsRemainingLabel = By.xpath("//*[contains(.,'CVs restantes')]");
    private final By usageByUserTitle = By.xpath("//h6[contains(.,'UTILIZACION POR USUARIO') or contains(.,'UTILIZACIÓN POR USUARIO')]");
    private final By progressBar = By.cssSelector("span[role='progressbar']");
    private final By periodLabel = By.xpath("//span[contains(.,'Abr') or contains(.,'May')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(title);
    }

    public String getTitle() {
        return waitVisible(title).getText();
    }

    public String getPlanName() {
        return waitVisible(premiumPlanBadge).getText();
    }

    public String getBillingPeriod() {
        return waitVisible(periodLabel).getText();
    }

    public String getMonthlyProgressText() {
        return waitVisible(monthlyProgressText).getText();
    }

    public boolean isCvsUsedVisible() {
        return isVisible(cvsUsedLabel);
    }

    public boolean isCvsRemainingVisible() {
        return isVisible(cvsRemainingLabel);
    }

    public boolean isUsageByUserChartVisible() {
        return isVisible(usageByUserTitle);
    }

    public String getProgressPercentageFromBar() {
        return waitVisible(progressBar).getAttribute("aria-label");
    }
}
