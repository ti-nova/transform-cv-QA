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

    /** Crea un ChromeDriver agregando, si está en CI, los flags requeridos. */
    public static WebDriver create(ChromeOptions options) {
        if (isRunningInCi()) {
            options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu"
            );
        }
        return new ChromeDriver(options);
    }

    private static boolean isRunningInCi() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }
}
