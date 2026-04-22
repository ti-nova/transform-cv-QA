# Testing

Proyecto de automatización de pruebas UI para `tranform-cv.vercel.app` usando Java, Selenium WebDriver, TestNG y Gradle.

## Tecnologías utilizadas

- Java
- Gradle
- Selenium WebDriver `4.41.0`
- TestNG `7.10.2`
- dotenv-java `3.0.0`

## Objetivo

Este proyecto automatiza flujos funcionales de la aplicación, siguiendo una estructura basada en:

- `Page Object Model`
- componentes reutilizables
- modales reutilizables
- tests organizados por módulo funcional

Actualmente incluye automatización para escenarios como:

- autenticación de usuario
- cambio de contraseña
- navegación a la página de transformación

## Estructura del proyecto

```text
testing/
├── src/
│   └── test/
│       └── java/
│           ├── config/
│           │   └── Config.java
│           ├── page/
│           │   ├── BasePage.java
│           │   ├── LogInPage.java
│           │   ├── TranformPage.java
│           │   ├── components/
│           │   └── modals/
│           └── testClass/
│               ├── auth/
│               │   └── LogInPageTest.java
│               └── tranform/
│                   └── TranformPageTest.java
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── .env
```
## Configuración
Este proyecto usa variables de entorno cargadas desde un archivo .env.

### Archivo .env
Crear un archivo .env en la raíz del proyecto con este formato:

USER_EMAIL=tu_correo@dominio.com
USER_PASSWORD=tu_password

### Clase de configuración
Las credenciales se leen desde config.Config:

Config.getUserEmail()
Config.getUserPassword()
### Requisitos
Antes de ejecutar las pruebas, asegúrate de tener:

Java instalado
Google Chrome instalado
ChromeDriver compatible con tu versión de Chrome
credenciales válidas en .env
## Ejecución de pruebas
### Ejecutar todas las pruebas
gradlew test

En Windows PowerShell:

.\gradlew test

## Casos automatizados actuales
### LogInPageTest
Incluye validaciones de:

login inválido
login válido
###TranformPageTest
Incluye validación de:

cambio de contraseña
restauración de la contraseña original al finalizar el test
##Patrón de diseño
El proyecto utiliza Page Object Model.

### BasePage
Centraliza utilidades comunes como:

espera de visibilidad
espera de click
visibilidad de elementos
acceso a componentes compartidos como:
SidebarComponent
UserMenuComponent
ChangePasswordModal
### Pages
Representan pantallas principales, por ejemplo:

LogInPage
TranformPage
### Components
Representan bloques reutilizables dentro de varias páginas, por ejemplo:

menú lateral
menú de usuario
### Modals
Representan diálogos o ventanas emergentes reutilizables, por ejemplo:

cambio de contraseña
## Convención actual de tests
Los tests están organizados por módulo funcional:

testClass.auth
testClass.tranform
Cada clase agrupa escenarios de una misma funcionalidad.

## Consideraciones
Algunos flujos pueden redirigir automáticamente al login después de una acción, como el cambio de contraseña.
El test de cambio de contraseña está diseñado para ser neutro: cambia la contraseña temporalmente y luego la restaura a su valor original.
El proyecto actualmente mezcla dos estilos en Pages:
PageFactory en LogInPage
By + WebDriverWait en BasePage y TranformPage
Como mejora futura, conviene unificar todo el proyecto hacia un solo enfoque.

## Mejoras futuras sugeridas
unificar todas las pages al estilo BasePage + By + WebDriverWait
agregar suites TestNG por módulo y por tipo de ejecución (smoke, regression)
agregar trazabilidad entre matriz de pruebas y casos automatizados
externalizar URLs por entorno
incorporar reportes más completos
## Notas
El archivo .env no debe subirse al repositorio.
Las carpetas generadas por ejecución como build/, .gradle/ y test-output/ deben estar en .gitignore.