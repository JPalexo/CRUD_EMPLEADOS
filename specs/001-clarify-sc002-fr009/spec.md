# Feature Specification: Clarificar SC-002 y FR-009

**Feature Branch**: `001-clarify-sc002-fr009`  
**Created**: 2026-03-10  
**Status**: Draft  
**Input**: User description: "para aclarar SC-002 y FR-009"

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

### User Story 1 - Métrica de concurrencia inequívoca (Priority: P1)

Como responsable de producto, quiero que SC-002 tenga una definición medible y sin ambiguedad para validar de forma objetiva el comportamiento de generación de claves en altas concurrentes.

**Why this priority**: SC-002 ambiguo impide aceptar o rechazar entregas de forma consistente.

**Independent Test**: Puede validarse revisando la especificación y comprobando que SC-002 define umbral de concurrencia, unicidad esperada y criterio explícito sobre secuencia numérica.

**Acceptance Scenarios**:

1. **Given** la especificación del feature de clave compuesta, **When** se revisa SC-002, **Then** la métrica incluye un volumen mínimo de altas concurrentes y un resultado esperado cuantificable.
2. **Given** la especificación del feature de clave compuesta, **When** se revisa SC-002, **Then** el criterio sobre continuidad de secuencia indica explícitamente qué casos son válidos o inválidos.

---

### User Story 2 - Restricciones de campos verificables (Priority: P2)

Como QA, quiero que FR-009 detalle límites y condiciones de validación para `nombre`, `direccion` y `telefono`, para derivar pruebas sin interpretaciones distintas.

**Why this priority**: FR-009 requiere precisión para evitar diferencias entre pruebas funcionales, contrato y aceptación.

**Independent Test**: Puede validarse comparando FR-009 contra un checklist de casos límite de longitud y formato para los tres campos.

**Acceptance Scenarios**:

1. **Given** la especificación del feature de clave compuesta, **When** se revisa FR-009, **Then** se identifican claramente límites de longitud y regla de rechazo para entradas fuera de límite.
2. **Given** la especificación del feature de clave compuesta, **When** se revisa FR-009, **Then** se indica explícitamente si la validación aplica a creación y actualización.

---

### User Story 3 - Consistencia documental transversal (Priority: P3)

Como mantenedor del repositorio, quiero que las aclaraciones queden reflejadas de forma consistente en especificación, plan y tareas para mantener trazabilidad y evitar regresiones de calidad.

**Why this priority**: La alineación evita reintroducir ambiguedades en iteraciones futuras.

**Independent Test**: Puede validarse verificando que las aclaraciones de SC-002 y FR-009 tienen referencia explícita en artefactos de planificación y checklist de calidad.

**Acceptance Scenarios**:

1. **Given** los artefactos de la feature de clave compuesta, **When** se contrastan con esta especificación, **Then** la definición aclarada de SC-002 y FR-009 es única y no conflictiva.

---

### Edge Cases

- Revisión de SC-002 sin umbral de concurrencia declarado: debe considerarse incompleta.
- Definición de FR-009 que mencione máximo 100 caracteres pero no indique comportamiento ante 101: debe considerarse ambigua.
- Definiciones que difieran entre creación y actualización sin indicarlo explícitamente: deben rechazarse por inconsistencia.
- Criterios que usen términos no verificables como "sin degradación" o "saltos inválidos" sin definición operativa: deben marcarse como no aceptables.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: La especificación del feature `002-emp-composite-key` MUST redefinir SC-002 con una métrica cuantificable que incluya tamaño de prueba concurrente mínimo y resultado esperado.
- **FR-002**: La definición aclarada de SC-002 MUST establecer que 100% de claves generadas en la prueba concurrente son únicas.
- **FR-003**: La definición aclarada de SC-002 MUST especificar la regla de secuencia aceptable del consecutivo numérico, incluyendo el tratamiento de huecos por operaciones no confirmadas.
- **FR-004**: La especificación del feature `002-emp-composite-key` MUST redefinir FR-009 para detallar reglas de validación de `nombre`, `direccion` y `telefono` en creación y actualización.
- **FR-005**: La definición aclarada de FR-009 MUST declarar que valores con longitud mayor a 100 caracteres se rechazan con error de validación explícito.
- **FR-006**: La definición aclarada de FR-009 MUST declarar la regla para valores vacíos o solo espacios en `nombre`, `direccion` y `telefono`.
- **FR-007**: Las aclaraciones de SC-002 y FR-009 MUST quedar trazadas en los artefactos de planificación y checklist de calidad asociados al feature `002-emp-composite-key`.

### Technical Constraints *(mandatory)*

- **TC-001**: Backend implementation MUST use Spring Boot 3 with Java 17.
- **TC-002**: Protected endpoints MUST enforce HTTP Basic Authentication.
- **TC-003**: Persistent data MUST be stored in PostgreSQL with versioned migrations.
- **TC-004**: Feature MUST run in Docker-based local environment.
- **TC-005**: Any API contract change MUST update OpenAPI/Swagger documentation.

### Key Entities *(include if feature involves data)*

- **ReglaSC002**: Define el criterio de aceptación para generación concurrente de claves, incluyendo volumen de prueba, unicidad y política de secuencia.
- **ReglaFR009**: Define las restricciones funcionales de `nombre`, `direccion` y `telefono`, incluyendo longitudes máximas y condiciones de validez de contenido.
- **EvidenciaTrazabilidad**: Registro de alineación entre especificación, plan, tareas y checklist para asegurar una única interpretación de requisitos.

## Assumptions

- El alcance de esta feature es documental y de calidad de especificación; no introduce endpoints nuevos.
- La restricción de longitud máxima de 100 caracteres para `nombre`, `direccion` y `telefono` se mantiene.
- Para valores vacíos o solo espacios, la regla por defecto será rechazo de validación en los tres campos.
- La política de secuencia permite huecos solo cuando provienen de operaciones no confirmadas, no por colisión o reutilización de consecutivos.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% de revisiones de especificación identifican un único criterio de aceptación para SC-002 sin términos ambiguos.
- **SC-002**: 100% de revisiones de especificación identifican reglas completas de FR-009 para `nombre`, `direccion` y `telefono` en creación y actualización.
- **SC-003**: 100% de escenarios de aceptación derivados de SC-002 y FR-009 son verificables sin requerir interpretación adicional del equipo.
- **SC-004**: 0 inconsistencias entre especificación, plan y tareas respecto a definiciones aclaradas de SC-002 y FR-009 al cierre de la revisión.
- **SC-005**: 100% de hallazgos de ambiguedad previos para SC-002 y FR-009 quedan cerrados en el checklist de requisitos.
