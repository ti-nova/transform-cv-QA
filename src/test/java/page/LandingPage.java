package page;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.modals.RegistrationModal;

public class LandingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Navbar
    private final By homeLink = By.cssSelector("a[href='#inicio']");
    private final By plansLink = By.cssSelector("a[href='#planes']");
    private final By contactLink = By.cssSelector("a[href='#contacto']");
    private final By loginButton = By.xpath("//nav//button[normalize-space()='Login']");

    // Hero
    private final By startNowButton = By.cssSelector("section#inicio .btn-primary");

    // Plans
    private final By basicPlanButton = By.xpath("//div[contains(@class,'card')][.//h3[normalize-space()='Plan Básico']]//button");
    private final By advancedPlanButton = By.xpath("//div[contains(@class,'card')][.//h3[normalize-space()='Plan Avanzado']]//button");
    private final By premiumPlanButton = By.xpath("//div[contains(@class,'card')][.//h3[normalize-space()='Plan Premium']]//button");
    private final By enterprisePlanButton = By.xpath("//div[contains(@class,'card')][.//h3[normalize-space()='Plan Empresarial']]//button");

    // Contact
    private final By contactTitle = By.xpath("//section[@id='contacto']//h2[normalize-space()='Contacto']");

    // Modal
    private final By registrationModal = By.cssSelector("div.modal-overlay div.modal");

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickHome() {
        wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
    }

    public void clickPlans() {
        wait.until(ExpectedConditions.elementToBeClickable(plansLink)).click();
    }

    public void clickContact() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public RegistrationModal clickStartNow() {
        wait.until(ExpectedConditions.elementToBeClickable(startNowButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
        return new RegistrationModal(driver);
    }

    public RegistrationModal selectBasicPlan() {
        wait.until(ExpectedConditions.elementToBeClickable(basicPlanButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
        return new RegistrationModal(driver);
    }

    public RegistrationModal selectAdvancedPlan() {
        wait.until(ExpectedConditions.elementToBeClickable(advancedPlanButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
        return new RegistrationModal(driver);
    }

    public RegistrationModal selectPremiumPlan() {
        wait.until(ExpectedConditions.elementToBeClickable(premiumPlanButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
        return new RegistrationModal(driver);
    }

    public RegistrationModal selectEnterprisePlan() {
        wait.until(ExpectedConditions.elementToBeClickable(enterprisePlanButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
        return new RegistrationModal(driver);
    }

    public String getContactTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(contactTitle)).getText();
    }

    public boolean isRegistrationModalVisible() {
        return !driver.findElements(registrationModal).isEmpty()
                && driver.findElement(registrationModal).isDisplayed();
    }
}
