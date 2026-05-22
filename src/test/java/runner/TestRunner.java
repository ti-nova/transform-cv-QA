package runner;

import java.awt.Desktop;
import java.io.File;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import reporting.FunnelHtmlReporter;
import testClass.auth.CierreSessionTest;
import testClass.auth.LogInPageTest;
import testClass.dashboard.DashboardTest;
import testClass.landing.LandingPageTest;
import testClass.pending.ConfiguracionApiKeyTest;
import testClass.pending.NotificacionesInternasTest;
import testClass.pending.RecuperacionContrasenaTest;
import testClass.processedcvs.ProcessedCvsTest;
import testClass.settings.OrganizationSettingsTest;
import testClass.settings.TeamSettingsTest;
import testClass.tranform.TranformCvTest;
import testClass.tranform.TranformPageTest;

/**
 * Punto de entrada para ejecutar TODA la suite de pruebas SIN linea de comandos.
 *
 * Uso en el IDE (IntelliJ / VS Code):
 *   click derecho sobre este archivo  ->  Run 'TestRunner.main()'.
 *
 * Caracteristicas:
 *  - Ejecuta todas las clases de prueba de forma SECUENCIAL (una por una;
 *    nunca hay dos navegadores abiertos al mismo tiempo).
 *  - Al finalizar, genera el reporte funnel y lo ABRE automaticamente en el
 *    navegador por defecto.
 *
 * Nota: requiere el archivo .env (USER_EMAIL / USER_PASSWORD) en la raiz del
 * proyecto, igual que cualquier ejecucion de la suite.
 */
public class TestRunner {

    private static final String OUTPUT_DIR = "test-output";

    public static void main(String[] args) {
        TestNG testng = new TestNG();

        // Orden de ejecucion de las clases de prueba.
        testng.setTestClasses(new Class[]{
                LogInPageTest.class,
                CierreSessionTest.class,
                LandingPageTest.class,
                DashboardTest.class,
                ProcessedCvsTest.class,
                OrganizationSettingsTest.class,
                TeamSettingsTest.class,
                TranformPageTest.class,
                TranformCvTest.class,
                RecuperacionContrasenaTest.class,
                ConfiguracionApiKeyTest.class,
                NotificacionesInternasTest.class
        });

        // Ejecucion secuencial: una prueba por vez, sin paralelismo.
        testng.setParallel(XmlSuite.ParallelMode.NONE);
        // Agrupa los metodos por clase: cada navegador se abre y cierra
        // mientras corre su clase, sin intercalar clases entre si.
        testng.setGroupByInstances(true);
        testng.setOutputDirectory(OUTPUT_DIR);
        testng.addListener(new FunnelHtmlReporter());

        testng.run();

        abrirReporte();
    }

    private static void abrirReporte() {
        File reporte = new File(OUTPUT_DIR, "reporte-funnel.html");
        if (!reporte.exists()) {
            System.err.println("No se encontro el reporte: " + reporte.getAbsolutePath());
            return;
        }
        try {
            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(reporte.toURI());
                System.out.println("Reporte funnel abierto en el navegador.");
            } else {
                System.out.println("Abre el reporte manualmente: " + reporte.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("No se pudo abrir el reporte automaticamente: " + e.getMessage());
            System.out.println("Ruta del reporte: " + reporte.getAbsolutePath());
        }
    }
}
