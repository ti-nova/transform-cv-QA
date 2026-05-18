package testClass.pending;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Configuración de API key
 * Estado: pendiente — falta Page Object y sección de API key en la app.
 */
public class ConfiguracionApiKeyTest {

    @Test
    public void generarApiKeyMuestraClaveNueva() {
        throw new SkipException("TODO: No implementado — falta Page Object para la sección de configuración de API key.");
    }

    @Test
    public void revocarApiKeyInvalidaAccesoAnterior() {
        throw new SkipException("TODO: No implementado — falta Page Object para la sección de configuración de API key.");
    }
}
