package page.modals;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationModal {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By companyInput = By.name("empresa");
    private final By firstNameInput = By.name("nombre");
    private final By lastNameInput = By.name("apellido");
    private final By emailInput = By.name("email");
    private final By continueToPaymentButton = By.xpath("//div[contains(@class,'modal-buttons')]//button[normalize-space()='Continuar al pago']");
    private final By cancelButton = By.xpath("//div[contains(@class,'modal-buttons')]//button[contains(@class,'cancel')]");

    public RegistrationModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterCompany(String company) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(companyInput)).clear();
        driver.findElement(companyInput).sendKeys(company);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void fillForm(String company, String firstName, String lastName, String email) {
        enterCompany(company);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
    }

    public void clickContinueToPayment() {
        wait.until(ExpectedConditions.elementToBeClickable(continueToPaymentButton)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }
}