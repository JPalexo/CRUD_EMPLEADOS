# Implementation Plan: Clave Compuesta EMP para Empleados

**Branch**: `002-emp-composite-key` | **Date**: 2026-03-04 | **Spec**: `/specs/002-emp-composite-key/spec.md`
**Input**: Feature specification from `/specs/002-emp-composite-key/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Evolucionar el CRUD de empleados para que el identificador pase de clave numérica simple a una clave compuesta lógica con formato `EMP-<consecutivo>`, autogenerada en altas, validada en operaciones de lectura/edición/borrado y soportada por migración de datos existentes, manteniendo seguridad HTTP Basic, persistencia en PostgreSQL con migraciones versionadas, ejecución Docker y documentación OpenAPI/Swagger actualizada.

## Technical Context

**Language/Version**: Java 17 (MUST; constitution requirement)  
**Primary Dependencies**: Spring Boot 3, Spring Security, Spring Data JPA, springdoc-openapi  
**Storage**: PostgreSQL con migraciones versionadas (Flyway) para transición de clave y preservación de integridad  
**Testing**: JUnit 5 + Spring Boot Test + MockMvc + Testcontainers PostgreSQL (unitarias, integración, contrato y migración)  
**Target Platform**: Linux container runtime (Docker)  
**Project Type**: Backend web-service (Spring Boot)  
**Performance Goals**: p95 < 300 ms en operaciones CRUD individuales, incluyendo validación de formato de clave compuesta  
**Constraints**: Prefijo fijo `EMP-`, consecutivo único no reutilizable, rechazo de clave manual en alta, validación de formato en endpoints de clave, no exposición de secretos en logs  
**Scale/Scope**: Catálogo interno hasta 10,000 empleados; sin paginación ni búsquedas avanzadas en esta iteración

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Stack gate: Uses Spring Boot 3 + Java 17 only.
- [x] Security gate: HTTP Basic Authentication defined for protected endpoints;
  credential sourcing via environment/secrets documented.
- [x] Persistence gate: PostgreSQL usage confirmed; migration/versioning strategy
  defined for schema changes.
- [x] Container gate: Docker execution plan (app + database) documented with local
  parity expectations.
- [x] API contract gate: OpenAPI/Swagger update scope defined for all endpoint changes.

### Post-Design Re-Check (after Phase 1)

- [x] Stack gate reflected in design artifacts.
- [x] Security gate reflected in auth requirements and contract responses.
- [x] Persistence gate reflected in migration-first data transition design.
- [x] Container gate reflected in quickstart and compose execution flow.
- [x] API contract gate reflected in updated key format and endpoint schemas.

## Project Structure

### Documentation (this feature)

```text
specs/002-emp-composite-key/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

```text
src/
└── main/
  ├── java/
  │   └── .../
  │       ├── controller/
  │       ├── service/
  │       ├── repository/
  │       ├── model/
  │       ├── dto/
  │       ├── config/
  │       └── exception/
  └── resources/
    ├── application.yml
    └── db/migration/

src/
└── test/
  └── java/
    └── .../
      ├── unit/
      ├── integration/
      └── contract/

docker/
├── Dockerfile
└── docker-compose.yml
```

**Structure Decision**: Proyecto backend único por capas (controller/service/repository) con transición de esquema vía migraciones versionadas y contrato API documentado en la misma entrega.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
