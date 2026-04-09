package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage {

    @FindBy(xpath = "//input[@type='email']")
    private WebElement inputUser;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement botonIngresar;

    public LogInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterUserEmail(String user) {
        inputUser.clear();
        inputUser.sendKeys(user);
    }

    public void enterPassword(String password) {
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void clickIngresar() {
        botonIngresar.click();
    }
}