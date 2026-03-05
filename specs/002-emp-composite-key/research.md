# Phase 0 Research - Clave Compuesta EMP para Empleados

## Decision 1: Modelo de clave compuesta lógica
- Decision: Exponer `clave` como string canónico `EMP-<consecutivo>` y tratarla como identidad primaria de negocio.
- Rationale: Cumple requerimiento funcional de prefijo fijo más autonumérico y simplifica consumo por clientes.
- Alternatives considered:
  - Mantener clave numérica separada y derivar prefijo solo en UI: rechazada por inconsistencia de contrato.
  - Usar UUID: rechazado por no cumplir formato requerido por negocio.

## Decision 2: Estrategia de generación del consecutivo
- Decision: Mantener contador numérico no reutilizable y generar consecutivo de forma atómica en persistencia.
- Rationale: Evita colisiones en altas concurrentes y garantiza unicidad.
- Alternatives considered:
  - Calcular `MAX+1` en servicio: rechazado por riesgo de carrera.
  - Reutilizar consecutivos de registros eliminados: rechazado por trazabilidad e integridad histórica.

## Decision 3: Persistencia y migración de datos legados
- Decision: Ejecutar migraciones versionadas para convertir claves existentes al formato `EMP-<número>` y preservar relaciones referenciales.
- Rationale: Constitución exige migraciones determinísticas en PostgreSQL.
- Alternatives considered:
  - Reescritura manual fuera de migración: rechazada por baja trazabilidad.
  - Reset de datos para evitar migrar: rechazado por pérdida de información.

## Decision 4: Validación de formato de clave
- Decision: Validar formato `^EMP-[0-9]+$` en entradas de ruta para consulta, actualización y eliminación.
- Rationale: Rechazo temprano de entradas inválidas y respuestas de error explícitas.
- Alternatives considered:
  - Permitir variantes (`emp-`, espacios, ceros ambiguos): rechazadas por ambigüedad operacional.

## Decision 5: Política de escritura de clave
- Decision: El endpoint de alta no acepta `clave` en request; el sistema la genera.
- Rationale: Evita manipulaciones del identificador y asegura consistencia del formato.
- Alternatives considered:
  - Aceptar clave opcional manual: rechazado por romper unicidad y control del consecutivo.

## Decision 6: Seguridad y exposición de endpoints
- Decision: Mantener HTTP Basic Authentication en endpoints CRUD; salud/documentación solo excepciones explícitas en implementación.
- Rationale: Alineado con constitución y requerimientos de seguridad.
- Alternatives considered:
  - Exponer listados sin autenticación: rechazado por incumplimiento de política.

## Decision 7: Contrato API y documentación
- Decision: Actualizar OpenAPI para reflejar `clave` string con patrón, respuestas 400 por formato inválido y 404 por no encontrado.
- Rationale: Evita discrepancias entre implementación y consumo de API.
- Alternatives considered:
  - Documentación parcial sin patrón: rechazada por ambigüedad para clientes.

## Decision 8: Estrategia de pruebas
- Decision: Cubrir generación concurrente, validación de formato, migración y operaciones CRUD completas con pruebas unitarias e integración.
- Rationale: Mitiga riesgos del cambio de identidad primaria.
- Alternatives considered:
  - Solo pruebas de endpoint nominal: rechazadas por no cubrir riesgos de migración/concurrencia.
