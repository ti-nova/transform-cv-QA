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
import config.Config;

public class LogInPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LogInPage loginPage;

    private final String urlLogin = "https://tranform-cv.vercel.app/login";

    @BeforeMethod
    public void setup() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LogInPage(driver);

        driver.manage().window().maximize();
        driver.get(urlLogin);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email']")
        ));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void ingresoUsuarioIncorrecto() {

        loginPage.enterUserEmail("usuario_invalido@test.com");
        loginPage.enterPassword("clave_invalida");
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'error') or contains(text(),'incorrect')]")
        ));

        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("error") ||
                driver.getPageSource().toLowerCase().contains("incorrect"),
                "No se mostró mensaje de error para login inválido"
        );
    }

    @Test
    public void ingresoUsuarioCorrecto() {

        loginPage.enterUserEmail(Config.getUserEmail());
        loginPage.enterPassword(Config.getUserPassword());
        loginPage.clickIngresar();

        wait.until(ExpectedConditions.urlContains("/transform"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/transform"),
                "No redirigió correctamente después del login"
        );
    }
}