## Inventario de tests automatizados (Selenium/Java) → “prose tests” y mapeo a User Stories

A partir del árbol de archivos provisto, inferí **casos de prueba en prosa** (estilo “Resultado esperado”) y los asocié a User Stories del CSV cuando es razonable por nombre/página/dominio.

> Importante:
> - Esto es una **estimación** basada en nombres de clases/páginas y testdata.  
> - Para confirmarlo al 100% habría que ver el contenido de `*Test.java` (asserts, flujos y qué valida cada test).
> - Considero “automatizado” si hay un test implementado en `src/test/java/testClass/...` que cubre ese comportamiento.

---

### 1) Prose tests deducidos desde Selenium (automatizados)

| Suite / Página | Prose test (descripción) | Evidencia (archivo) | User Story candidata | ¿Automatizada? |
|---|---|---|---|---|
| Login | Acceso correcto al sistema con credenciales válidas | `testClass/auth/LogInPageTest.java`, `page/LogInPage.java` | **Autenticación de usuario (117)** | Sí |
| Login | Rechaza acceso y muestra error con credenciales inválidas | `testClass/auth/LogInPageTest.java` | **Autenticación de usuario (117)** | Sí |
| Dashboard | Al autenticarse se muestra panel (dashboard) y navegación base | `testClass/dashboard/DashboardTest.java`, `page/DashboardPage.java` | **Autenticación de usuario (117)** (o smoke post-login) | Sí |
| Cambio de contraseña | Cambiar contraseña con datos válidos confirma actualización | `page/modals/ChangePasswordModal.java` (y probable uso en algún `*Test`) | **Cambio de contraseña (119)** | **Probable Sí** *(ver nota)* |
| Cambio de contraseña | Nueva contraseña inválida es rechazada con mensaje | `ChangePasswordModal.java` | **Cambio de contraseña (119)** | **Probable Sí** *(ver nota)* |
| Transformación CV (carga válida) | Permite cargar **PDF válido** y habilita transformación | `testClass/tranform/TranformCvTest.java`, `testdata/.../cv_valido.pdf` | **Transformar CV (126)** | Sí |
| Transformación CV (carga válida) | Permite cargar **DOCX válido** y habilita transformación | `TranformCvTest.java`, `cv_valido.docx` | **Transformar CV (126)** | Sí |
| Transformación CV (archivo corrupto) | Rechaza PDF corrupto y muestra error | `TranformCvTest.java`, `cv_corrupto.pdf` | **Transformar CV (126)** | Sí |
| Transformación CV (extensión inválida) | Rechaza archivo `.txt` (formato no permitido) | `TranformCvTest.java`, `archivo_invalido.txt` | **Transformar CV (126)** | Sí |
| Transformación CV (validación estructura mínima) | Rechaza CV sin datos personales | `TranformCvTest.java`, `cv_sin_datos_personales.pdf` | **Transformar CV (126)** | Sí |
| Transformación CV (validación estructura mínima) | Rechaza CV sin experiencia | `TranformCvTest.java`, `cv_sin_experiencia.pdf` | **Transformar CV (126)** | Sí |
| Transformación CV (validación estructura mínima) | Rechaza CV sin educación (docx) | `TranformCvTest.java`, `cv_sin_educacion.docx` | **Transformar CV (126)** | Sí |
| Historial / procesados | Se despliega listado de CV transformados (historial) y acceso a detalle/descarga (si aplica) | `page/ProcessedCvsPage.java` (+ probable test) | **Gestión de CV transformados (124)** | **Posible** *(no hay `ProcessedCvsTest.java` visible)* |
| Config empresa | Accede a configuración de organización y visualiza/edita campos (nombre/desc/logo/url bd) | `page/OrganizationSettingsPage.java` (+ probable test) | **Configuración de empresa (125)** | **Posible** *(no hay test visible)* |
| Equipo/roles | Accede a configuración de equipo (team settings) / listado de usuarios | `page/TeamSettingsPage.java` | **Gestión de roles y permisos (122)** | **Posible** *(no hay test visible)* |
| Landing/Registro | Visualiza landing y apertura modal de registro/selección plan | `page/LandingPage.java`, `page/modals/RegistrationModal.java` | **Selección de plan y registro de empresa (115)** | **Posible** *(no hay `RegistrationTest.java` visible)* |

**Notas de incertidumbre**
- **Cambio de contraseña (119):** existe `ChangePasswordModal.java`, pero **no se ve un `*Test.java` explícito** en el listado que lo use. Podría estar incluido dentro de `DashboardTest.java` o algún `*PageTest.java`. Por eso lo marco “Probable Sí”.
- Existen Page Objects para settings/roles/registration/historial, pero **sin test explícito** listado: los marco “Posible”.

---

### 2) Comparación contra el CSV de casos de prueba (“prose” existentes)

Del CSV de test cases, los casos (no vacíos) eran 17 filas útiles, pero para cobertura por **User Story** lo relevante es qué US aparecen.

#### 2.1 User Stories con casos de prueba en CSV (manual o automatizable)
(Esto viene del CSV original de test cases)

- Despliegue de logo y empresa… (11) — No aplica (manual)
- Suspender y activar cuenta cliente (13) — No aplica (manual)
- Crear nuevo cliente (14) — No aplica (manual)
- Editar cliente (15) — No aplica (manual)
- Eliminar Clientes (16) — No aplica (manual)
- Asignar o cambiar plan cliente (17) — No aplica (manual)
- Consultar métricas… (18) — No aplica (manual)
- Crear empresas (114) — No aplica (manual)
- Selección de plan y registro de empresa (115) — No (no automatizado en CSV)
- Plataforma de pago (116) — No (no automatizado en CSV)
- Autenticación de usuario (117) — Sí (automatizado en CSV)
- Cierre de sesión (118) — Sí (automatizado en CSV)
- Cambio de contraseña (119) — Sí (automatizado en CSV)
- (Además hay un caso “Solo deja cargar archivos en PDF” sin User Story asociada → no cuenta)

---

### 3) Cobertura actualizada: (A) Cobertura de User Stories por *cualquier* test vs (B) Cobertura de esas User Stories por tests automatizados

#### 3.1 Cobertura de User Stories por test cases (CSV) — baseline
- Total User Stories en backlog (CSV HDU): **20**
- User Stories cubiertas por al menos 1 caso de prueba en CSV: **13**
- **Cobertura (tests vs US): 13 / 20 = 65%**

*(igual que el cálculo anterior)*

#### 3.2 Cobertura de automatización sobre las User Stories que tienen tests
Ahora medimos: de las User Stories que tienen al menos un caso (las 13 anteriores), ¿cuáles están efectivamente automatizadas según tu repo Selenium?

**Automatizadas con alta confianza desde el repo:**
- **Autenticación de usuario (117)** ✅ (`LogInPageTest.java`)
- **Transformar CV (126)** ✅ (existe `TranformCvTest.java`)  
  - Ojo: **esta US NO estaba en el CSV de test cases mapeada**, pero sí hay automatización real.

**Automatizadas según CSV (y plausibles en repo pero no 100% confirmadas por listado):**
- **Cambio de contraseña (119)** ↔️ (CSV dice “Sí”; repo tiene modal, test no visible)
- **Cierre de sesión (118)** ↔️ (CSV dice “Sí”; repo tiene `UserMenuComponent.java`, test no visible)

Para no inventar, calculo dos métricas:

##### Métrica estricta (solo lo confirmable por nombres de *Test.java*)
- User Stories con test en CSV: **13**
- De esas, confirmables como automatizadas por repo: **1**  
  - (Solo **Autenticación (117)** aparece claramente automatizada y además está en CSV)
- **Cobertura de automatización (estricta) sobre US con tests: 1 / 13 = 7.69%**

##### Métrica extendida (tomando como automatizadas las que el CSV marca “Sí” y el repo sugiere soporte)
- User Stories con test en CSV: **13**
- Automatizadas (CSV “Sí”): **3** → (117, 118, 119)
- **Cobertura de automatización (extendida) sobre US con tests: 3 / 13 = 23.08%**

---

### 4) Cobertura total de automatización contra todo el backlog (20 US)

De las 20 User Stories, ¿cuáles tienen automatización implementada (confirmable) en el repo?

**Confirmable por repo:**
- **Autenticación de usuario (117)** ✅
- **Transformar CV (126)** ✅

=> **2 / 20 = 10%** de cobertura de automatización total (estricta).

**Extendida (sumando 118 y 119 por CSV “Sí”, aunque el repo listado no muestre el test explícito):**
- 117, 118, 119, 126 → **4 / 20 = 20%**

---

### 5) Resumen ejecutivo

- **Cobertura funcional (tests vs US, desde CSV):** **65%** (13/20 US tienen al menos 1 test)
- **Cobertura de automatización (estricta, confirmable por repo):**
  - Sobre US con tests: **7.69%** (1/13)
  - Sobre backlog total: **10%** (2/20) *(incluye Transformar CV que no está mapeada en CSV)*
- **Cobertura de automatización (extendida, usando etiqueta CSV “Sí”):**
  - Sobre US con tests: **23.08%** (3/13)
  - Sobre backlog total: **20%** (4/20)
