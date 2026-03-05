# Feature Specification: Clave Compuesta EMP para Empleados

**Feature Branch**: `002-emp-composite-key`  
**Created**: 2026-03-02  
**Status**: Ready  
**Input**: User description: "Modificar la especificación de empleados para que la clave sea un prefijo EMP- seguido de un autonumérico como PK compuesta"

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Alta con clave generada (Priority: P1)

Como usuario autenticado, quiero registrar empleados sin capturar manualmente la clave para que el sistema asigne automáticamente una clave compuesta con formato `EMP-` + consecutivo numérico.

**Why this priority**: La generación automática y estandarizada de clave es el cambio central y habilita consistencia de datos desde el alta.

**Independent Test**: Puede validarse creando empleados nuevos y verificando que cada uno recibe una clave única con prefijo `EMP-` y consecutivo incremental.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado, **When** registra un empleado válido sin enviar clave, **Then** el sistema crea el empleado y asigna una clave con formato `EMP-<número>`.
2. **Given** un usuario autenticado y empleados existentes, **When** registra un nuevo empleado, **Then** el consecutivo numérico asignado es mayor al último consecutivo existente.
3. **Given** un usuario autenticado, **When** envía una alta con clave manual, **Then** el sistema rechaza la solicitud por campo no permitido en la creación.

---

### User Story 2 - Consulta por clave compuesta (Priority: P2)

Como usuario autenticado, quiero consultar y listar empleados usando la nueva clave compuesta para localizar registros con el identificador de negocio estandarizado.

**Why this priority**: Asegura continuidad operativa después del cambio de formato de clave y evita pérdida de capacidad de consulta.

**Independent Test**: Puede validarse consultando por claves válidas e inválidas del tipo `EMP-<número>` y confirmando respuestas correctas.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado y un empleado existente con clave `EMP-102`, **When** consulta por esa clave, **Then** el sistema devuelve el empleado correspondiente.
2. **Given** un usuario autenticado, **When** consulta una clave con formato inválido, **Then** el sistema responde con error de validación.
3. **Given** un usuario autenticado, **When** consulta una clave con formato válido pero inexistente, **Then** el sistema responde recurso no encontrado.

---

### User Story 3 - Transición de datos y operación CRUD completa (Priority: P3)

Como usuario autenticado, quiero que edición y eliminación funcionen sobre la nueva clave y que los registros existentes migren a dicho formato, para mantener operación continua sin ambigüedad de identificadores.

**Why this priority**: Garantiza integridad operacional y evita ruptura de datos históricos tras el cambio de clave primaria.

**Independent Test**: Puede validarse migrando datos previos, ejecutando actualización y eliminación por clave compuesta y confirmando que no hay duplicados ni claves huérfanas.

**Acceptance Scenarios**:

1. **Given** datos existentes previos al cambio, **When** se ejecuta la migración, **Then** todos los empleados quedan con clave en formato `EMP-<número>` sin duplicados.
2. **Given** un usuario autenticado y un empleado con clave compuesta existente, **When** actualiza nombre, dirección o teléfono, **Then** el sistema guarda cambios sin alterar la clave.
3. **Given** un usuario autenticado y un empleado con clave compuesta existente, **When** lo elimina por clave, **Then** el sistema confirma la baja y deja de retornarlo en consultas.

---

### Edge Cases

- Alta concurrente de múltiples empleados en el mismo instante: cada alta debe recibir clave única, sin colisiones de consecutivo.
- Existencia de empleados legados con clave no compatible: la migración debe normalizarlos al formato `EMP-<número>` o detenerse con reporte explícito.
- Consulta/actualización/eliminación con claves como `EMP-`, `EMP-ABC`, `emp-10` o `EMP--10`: deben rechazarse por formato inválido.
- Migración interrumpida: el sistema no debe quedar en estado parcialmente inconsistente de claves.
- Solicitudes sin autenticación o con credenciales inválidas: deben rechazarse en todos los endpoints protegidos.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST generar automáticamente la clave de empleado al crear registros, con formato `EMP-` seguido de un consecutivo numérico.
- **FR-002**: La clave MUST comportarse como clave primaria compuesta de dos componentes lógicos: prefijo fijo `EMP-` y segmento numérico autogenerado.
- **FR-003**: El segmento numérico de la clave MUST ser único y no reutilizable.
- **FR-004**: El sistema MUST impedir que clientes definan o modifiquen manualmente la clave durante creación o actualización.
- **FR-005**: El sistema MUST mantener operaciones de listado, consulta, actualización y eliminación usando la nueva clave compuesta.
- **FR-006**: El sistema MUST validar estrictamente el formato de clave recibida en operaciones de consulta/actualización/eliminación contra el patrón `^EMP-[0-9]+$`.
- **FR-007**: El sistema MUST migrar registros existentes para que todas las claves activas cumplan el formato `EMP-<número>`.
- **FR-008**: El sistema MUST preservar unicidad e integridad referencial de empleados durante y después de la migración de clave.
- **FR-009**: El sistema MUST mantener las restricciones actuales de `nombre`, `direccion` y `telefono` con longitud máxima de 100 caracteres.
- **FR-010**: El sistema MUST rechazar solicitudes con formato de clave inválido con estado `400` y código de error de validación explícito.
- **FR-011**: El sistema MUST exigir autenticación para operar sobre endpoints del CRUD de empleados.
- **FR-012**: El sistema MUST proveer documentación API actualizada con el nuevo formato de clave y reglas de validación.

### Technical Constraints *(mandatory)*

- **TC-001**: Backend implementation MUST use Spring Boot 3 with Java 17.
- **TC-002**: Protected endpoints MUST enforce HTTP Basic Authentication.
- **TC-003**: Persistent data MUST be stored in PostgreSQL with versioned migrations.
- **TC-004**: Feature MUST run in Docker-based local environment.
- **TC-005**: Any API contract change MUST update OpenAPI/Swagger documentation.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa un registro de personal con atributos de negocio (`nombre`, `direccion`, `telefono`) y una clave compuesta lógica (`prefijo`, `consecutivo`) expuesta como identificador canónico (`EMP-<número>`).
- **ClaveEmpleado**: Representa la identidad del empleado. Componentes: `prefijo` fijo (`EMP-`) y `consecutivo` numérico autogenerado; ambos determinan unicidad del identificador.
- **RegistroMigracionClave**: Representa evidencia del proceso de transición de claves legadas al nuevo formato, con resultado por registro y estado de consistencia.

## Assumptions

- El sistema asigna la clave automáticamente; los clientes no envían `clave` al crear empleados.
- El prefijo permitido para esta versión es exclusivamente `EMP-` en mayúsculas.
- El consecutivo numérico inicia en 1 para instalaciones nuevas y continúa desde el mayor existente en instalaciones con datos.
- El cambio aplica a todo el CRUD de empleados y no introduce búsquedas avanzadas ni nuevos roles de autorización.
- Política de autenticación explícita: en `dev`, `/actuator/health`, `/swagger-ui/**` y `/v3/api-docs/**` pueden quedar exentos; en `prod`, esos endpoints MUST requerir autenticación.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% de empleados creados después del cambio reciben clave con formato `EMP-<número>` sin intervención manual.
- **SC-002**: 100% de claves generadas en altas concurrentes son únicas y sin saltos inválidos de formato.
- **SC-003**: 100% de consultas/actualizaciones/eliminaciones con claves de formato inválido son rechazadas con error de validación explícito.
- **SC-004**: 100% de operaciones sobre claves compuestas inexistentes retornan resultado de no encontrado.
- **SC-005**: 100% de endpoints protegidos rechazan solicitudes sin autenticación válida.
- **SC-006**: 100% de endpoints nuevos o modificados muestran en documentación API el formato de clave `EMP-<número>` y sus restricciones.
