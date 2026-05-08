## Cobertura de casos de prueba vs User Stories

### 1) User Stories (HDU) detectadas (total: 20)

1. **[11]** Despliegue de logo y empresa para sesión de colaboradores  
2. **[13]** Suspender y activar cuenta cliente  
3. **[14]** Crear nuevo cliente  
4. **[15]** Editar cliente  
5. **[16]** Eliminar Clientes  
6. **[17]** Asignar o cambiar plan cliente  
7. **[18]** Consultar métricas por el listado de clientes  
8. **[19]** Configuración de API key  
9. **[116]** Plataforma de pago (Mercado Pago)  
10. **[114]** Crear empresas  
11. **[115]** Selección de plan y registro de empresa  
12. **[121]** Recuperación de contraseña  
13. **[117]** Autenticación de usuario  
14. **[122]** Gestión de roles y permisos  
15. **[118]** Cierre de sesión  
16. **[123]** Notificaciones internas  
17. **[119]** Cambio de contraseña  
18. **[124]** Gestión de CV transformados  
19. **[125]** Configuración de empresa  
20. **[126]** Transformar CV  

---

### 2) User Stories cubiertas por tests (13/20)

> Regla: una US se considera **cubierta** si existe al menos 1 test case cuyo campo **"User Stories"** está poblado y coincide con el título de la HDU.

| User Story | # tests asociados | Estado(s) Caso de prueba (según CSV) |
|---|---:|---|
| Suspender y activar cuenta cliente | 2 | Aprobado (x2) |
| Crear nuevo cliente | 1 | Aprobado |
| Editar cliente | 1 | Aprobado |
| Eliminar Clientes | 1 | Aprobado |
| Asignar o cambiar plan cliente | 1 | Aprobado |
| Crear empresas | 1 | Aprobado |
| Selección de plan y registro de empresa | 1 | Pendiente |
| Plataforma de pago (Mercado Pago) | 2 | Pendiente (x2) |
| Autenticación de usuario | 2 | Aprobado (x2) |
| Cambio de contraseña | 2 | Aprobado (x2) |
| Cierre de sesión | 1 | Aprobado |
| Consultar métricas por el listado de clientes | 1 | Aprobado |
| Despliegue de logo y empresa para sesión de colaboradores | 1 | Aprobado |

---

### 3) User Stories NO cubiertas por tests (7/20)

- Configuración de API key (**[19]**)
- Recuperación de contraseña (**[121]**)
- Gestión de roles y permisos (**[122]**)
- Notificaciones internas (**[123]**)
- Gestión de CV transformados (**[124]**)
- Configuración de empresa (**[125]**)
- Transformar CV (**[126]**)

**Nota:** existe un test case `"Solo deja cargar archivos en PDF"` pero tiene el campo **"User Stories"** vacío, por lo que **no cuenta** como cobertura.

---

### 4) Cobertura total (%)

- **Total User Stories:** 20  
- **User Stories cubiertas:** 13  

**Coverage = (13 / 20) * 100 = 65%**
