package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Factory que crea instancias de WebDriver con la configuración adecuada
 * para el entorno actual.
 *
 * En entornos de CI (GitHub Actions, GitLab CI) Chrome corre como root y
 * necesita flags adicionales para arrancar:
 *   --no-sandbox             permite ejecutar Chrome como root.
 *   --disable-dev-shm-usage  evita errores por la partición /dev/shm pequeña
 *                            que tienen los contenedores de los runners.
 *   --disable-gpu            no hay GPU disponible en runners headless.
 *
 * Estos flags se aplican solo si la variable de entorno CI=true (GitHub
 * Actions y GitLab CI la setean automáticamente). Localmente las opciones
 * no se modifican.
 */
public class DriverFactory {

    /** Crea un ChromeDriver con opciones por defecto + flags de CI si aplica. */
    public static WebDriver create() {
        return create(new ChromeOptions());
    }

    /**
     * Crea un ChromeDriver listo para usar.
     *  - En CI: agrega los flags requeridos por el runner y fija el tamaño de
     *    ventana con --window-size (NO se llama a maximize(), porque en
     *    Chrome 148 + xvfb maximize() falla con "Runtime.evaluate not found").
     *  - Localmente: arranca Chrome normal y maximiza la ventana.
     */
    public static WebDriver create(ChromeOptions options) {
        boolean ci = isRunningInCi();
        if (ci) {
            options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080"
            );
        }
        WebDriver driver = new ChromeDriver(options);
        if (!ci) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static boolean isRunningInCi() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }
}
