package testClass.pending;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Recuperación de contraseña
 * Estado: pendiente — falta Page Object y flujo de reset por email.
 */
public class RecuperacionContrasenaTest {

    @Test
    public void recuperarContrasenaConEmailValidoEnviaCorreo() {
        throw new SkipException("TODO: No implementado — falta Page Object para el flujo de recuperación de contraseña.");
    }

    @Test
    public void recuperarContrasenaConEmailInvalidoMuestraError() {
        throw new SkipException("TODO: No implementado — falta Page Object para el flujo de recuperación de contraseña.");
    }
}
