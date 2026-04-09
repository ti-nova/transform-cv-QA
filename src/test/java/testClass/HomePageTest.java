package testClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import page.LogInPage;
import page.HomePage;
import config.Config;

public class HomePageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(urlLogin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.xpath("//input[@type='email']")
        ));
        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();
        wait.until(ExpectedConditions.urlContains("/transform"));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cambiarContraseña() {
        HomePage homePage = new HomePage(driver);
        homePage.openUserMenu();
        homePage.openChangePasswordModal();
        homePage.changePassword(Config.getUserPassword(), "ABC12345", "ABC12345");
        homePage.openUserMenu();
        homePage.logout();
    }
}