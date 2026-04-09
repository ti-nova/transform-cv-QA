package testClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import page.HomePage;
import page.LogInPage;
import config.Config;

public class LogInPageTest {
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
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void ingresoUsuarioIncorrecto() {
        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail("usuario_invalido@test.com");
        loginPage.enterPassword("clave_invalida");
        loginPage.clickIngresar();
    }

    @Test
    public void ingresoUsuarioCorrecto() {
        LogInPage loginPage = new LogInPage(driver);
        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();
        wait.until(ExpectedConditions.urlContains("/transform"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/transform"));

    }
}