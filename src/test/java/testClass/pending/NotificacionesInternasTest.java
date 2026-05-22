package testClass.pending;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Notificaciones internas
 * Matriz de Pruebas: sin fila en el CSV (User Story sin caso de prueba definido).
 * Estado: pendiente — falta Page Object y módulo de notificaciones en la app.
 */
public class NotificacionesInternasTest {

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Notificaciones internas.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Notificacion aparece al completar transformacion | US: Notificaciones internas")
    public void notificacionApareceAlCompletarTransformacion() {
        throw new SkipException("TODO: No implementado — falta Page Object para el módulo de notificaciones internas.");
    }

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Notificaciones internas.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Marcar notificacion como leida la oculta del contador | US: Notificaciones internas")
    public void marcarNotificacionComoLeidaLaOcultaDelContador() {
        throw new SkipException("TODO: No implementado — falta Page Object para el módulo de notificaciones internas.");
    }
}
