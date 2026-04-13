# 🧪 Automatización QA - Transform CV

Proyecto de automatización de pruebas funcionales para la aplicación web **Transform CV**, utilizando Selenium WebDriver, TestNG y Java.

## 🚀 Tecnologías utilizadas

- Java
- Selenium WebDriver
- TestNG
- Gradle
- Dotenv (manejo de variables de entorno)

## 📂 Estructura del proyecto

src
└── test
     └── java
          ├── config
          │    └── Config.java
          ├── page
          │    ├── LogInPage.java
          │    └── HomePage.java
          ├── testClass
          │    ├── LogInPageTest.java
          │    └── HomePageTest.java
          └── testSuite
               └── Prueba.java


## ⚙️ Configuración

### 1. Clonar el repositorio

git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo

### 2. Crear archivo `.env`

En la raíz del proyecto:

USER_EMAIL=tu_email
USER_PASSWORD=tu_password

### 3. Instalar dependencias

Si usas Gradle:

gradle build


## ▶️ Ejecución de pruebas

Ejecutar:

testSuite.Prueba

## 🧪 Casos de prueba incluidos

### 🔐 Login

* Login con credenciales válidas ✅
* Login con credenciales inválidas ❌

### 👤 Usuario

* Cambio de contraseña
* Cierre de sesión

## 🧱 Patrón de diseño

Se implementa el patrón **Page Object Model (POM)** para:

* Mejorar mantenibilidad
* Separar lógica de UI y tests
* Reutilizar componentes

## ⚠️ Consideraciones

* No subir el archivo `.env` al repositorio
* Asegurarse de tener Google Chrome instalado
* Compatible con versiones recientes de Selenium

## 📌 Mejoras futuras

* Implementar BaseTest
* Integrar reportes (ExtentReports o Allure)
* Manejo de datos dinámicos
* CI/CD (GitHub Actions)