package testClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import org.openqa.selenium.By;

import page.LogInPage;
import page.HomePage;
import config.Config;

public class HomePageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LogInPage loginPage;
    private HomePage homePage;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeMethod
    public void setup() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LogInPage(driver);
        homePage = new HomePage(driver);

        driver.manage().window().maximize();
        driver.get(urlLogin);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email']")
        ));

        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.urlContains("/transform"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cambiarContraseña() {

        homePage.openUserMenu();
        homePage.openChangePasswordModal();

        homePage.changePassword(
                Config.getUserPassword(),
                "ABC12345",
                "ABC12345"
        );

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Guardar')]")
        ));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/transform"),
                "La operación no se completó correctamente"
        );

        homePage.openUserMenu();
        homePage.logout();

        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "No se cerró sesión correctamente"
        );
    }
}