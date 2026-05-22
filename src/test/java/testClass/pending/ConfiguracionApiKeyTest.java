package testClass.pending;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Configuración de API key
 * Matriz de Pruebas: sin fila en el CSV (User Story sin caso de prueba definido).
 * Estado: pendiente — falta Page Object y sección de API key en la app.
 */
public class ConfiguracionApiKeyTest {

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Configuración de API key.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Generar API key muestra clave nueva | US: Configuracion de API key")
    public void generarApiKeyMuestraClaveNueva() {
        throw new SkipException("TODO: No implementado — falta Page Object para la sección de configuración de API key.");
    }

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Configuración de API key.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Revocar API key invalida acceso anterior | US: Configuracion de API key")
    public void revocarApiKeyInvalidaAccesoAnterior() {
        throw new SkipException("TODO: No implementado — falta Page Object para la sección de configuración de API key.");
    }
}
