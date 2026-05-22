package testClass.pending;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * US: Recuperación de contraseña
 * Matriz de Pruebas: sin fila en el CSV (User Story sin caso de prueba definido).
 * Estado: pendiente — falta Page Object y flujo de reset por email.
 */
public class RecuperacionContrasenaTest {

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Recuperación de contraseña.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Recuperar contrasena con email valido envia correo | US: Recuperacion de contrasena")
    public void recuperarContrasenaConEmailValidoEnviaCorreo() {
        throw new SkipException("TODO: No implementado — falta Page Object para el flujo de recuperación de contraseña.");
    }

    /**
     * Matriz de Pruebas -> Sin fila en el CSV.
     * User Story: Recuperación de contraseña.
     * Estado: NO IMPLEMENTADA — stub que se omite vía SkipException.
     */
    @Test(description = "NO IMPLEMENTADA | Recuperar contrasena con email invalido muestra error | US: Recuperacion de contrasena")
    public void recuperarContrasenaConEmailInvalidoMuestraError() {
        throw new SkipException("TODO: No implementado — falta Page Object para el flujo de recuperación de contraseña.");
    }
}
