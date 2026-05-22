package testClass.landing;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Selección de plan y registro de empresa (Matriz de Pruebas: MTX-08).
 *
 * Estado: NO AUTOMATIZABLE en el deployment actual.
 *
 * Se verificó (mayo 2026, con inspección directa del DOM) que la app desplegada
 * en https://tranform-cv.vercel.app NO expone una página pública de landing ni
 * de selección de plan: la raíz "/" redirige directamente al login, y las rutas
 * /landing, /inicio, /home, /planes, /registro, /register, /signup y /pricing
 * no renderizan contenido.
 *
 * Mientras ese feature no esté desplegado, estas pruebas quedan como stubs
 * (SkipException) y aparecen como "No implementadas" en el reporte funnel.
 * Los Page Objects page.LandingPage y page.modals.RegistrationModal se
 * conservan para cuando la landing vuelva a estar disponible.
 */
public class LandingPageTest {

    private static final String MOTIVO =
            "NO AUTOMATIZABLE: el deployment actual no expone una landing pública "
            + "con selección de plan (la raíz redirige al login).";

    /**
     * Matriz de Pruebas -> MTX-08
     * Resultado esperado (CSV): "Avanza al flujo de pago si válido"
     * User Story: Selección de plan y registro de empresa.
     */
    @Test(description = "MTX-08 | Seleccion de plan abre el registro | US: Seleccion de plan y registro de empresa")
    public void seleccionDePlanAbreModalRegistro() {
        throw new SkipException(MOTIVO);
    }

    /**
     * Matriz de Pruebas -> MTX-08
     * Resultado esperado (CSV): "Avanza al flujo de pago si válido"
     * User Story: Selección de plan y registro de empresa.
     */
    @Test(description = "MTX-08 | Registro de empresa completa el flujo | US: Seleccion de plan y registro de empresa")
    public void registroDeEmpresaCompletaElFlujo() {
        throw new SkipException(MOTIVO);
    }
}
