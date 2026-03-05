# Tasks: Clave Compuesta EMP para Empleados

**Input**: Design documents from `/specs/002-emp-composite-key/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen pruebas porque la especificación exige resultados medibles para generación de clave, migración e integridad en operaciones CRUD.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and base runtime configuration

- [X] T001 Initialize Spring Boot 3 + Java 17 project dependencies for feature branch in pom.xml
- [X] T002 Configure base application properties and environment placeholders in src/main/resources/application.yml
- [X] T003 [P] Add/update Docker image build config for app runtime in docker/Dockerfile
- [X] T004 [P] Add/update local app + PostgreSQL orchestration in docker/docker-compose.yml
- [X] T005 [P] Add/update environment variable template in .env.example
- [X] T006 Create baseline package structure for controller/service/repository/model/dto/config/exception in src/main/java/com/crudempleados/

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Core enablers that MUST be complete before any user story implementation

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [X] T007 Add migration to evolve empleados key model to composite key fields in src/main/resources/db/migration/V2__empleados_composite_key.sql
- [X] T008 Add migration support objects for atomic numeric sequence generation in src/main/resources/db/migration/V3__empleados_key_sequence.sql
- [X] T009 [P] Refactor Empleado persistence model for `clave_prefijo` + `clave_consecutivo` in src/main/java/com/crudempleados/model/EmpleadoEntity.java
- [X] T010 [P] Refactor repository to query by composite key and canonical key in src/main/java/com/crudempleados/repository/EmpleadoRepository.java
- [X] T011 [P] Refactor DTOs for create/update separation and key exposure in src/main/java/com/crudempleados/dto/EmpleadoCreateRequest.java and src/main/java/com/crudempleados/dto/EmpleadoUpdateRequest.java
- [X] T012 Implement canonical key formatter/parser utility in src/main/java/com/crudempleados/service/ClaveEmpleadoFormatter.java
- [X] T013 Implement global key-format validation and API error mapping in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [X] T014 [P] Enforce HTTP Basic auth configuration with externalized credentials in src/main/java/com/crudempleados/config/SecurityConfig.java
- [X] T015 [P] Configure OpenAPI security scheme and key pattern metadata in src/main/java/com/crudempleados/config/OpenApiConfig.java
- [X] T016 [P] Add structured logging for auth failures and migration execution in src/main/resources/application.yml

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Alta con clave generada (Priority: P1) 🎯 MVP

**Goal**: Crear empleados sin clave manual y devolver clave canónica `EMP-<número>` autogenerada.

**Independent Test**: Crear múltiples empleados autenticados sin `clave` y comprobar formato canónico, unicidad y orden incremental del consecutivo.

### Tests for User Story 1

- [X] T017 [P] [US1] Add contract test for POST /api/v1/empleados request/response schema in src/test/java/com/crudempleados/contract/EmpleadoCreateContractTest.java
- [X] T018 [P] [US1] Add integration test for auto-generated key format on create in src/test/java/com/crudempleados/integration/EmpleadoCreateGeneratedKeyIntegrationTest.java
- [X] T019 [P] [US1] Add integration test rejecting manual `clave` input on create in src/test/java/com/crudempleados/integration/EmpleadoCreateManualKeyRejectedIntegrationTest.java
- [X] T020 [P] [US1] Add concurrency integration test for unique key generation in src/test/java/com/crudempleados/integration/EmpleadoCreateConcurrentIntegrationTest.java

### Implementation for User Story 1

- [X] T021 [US1] Implement atomic key-generation logic in src/main/java/com/crudempleados/service/ClaveEmpleadoGeneratorService.java
- [X] T022 [US1] Implement create flow using generated key in src/main/java/com/crudempleados/service/EmpleadoService.java
- [X] T023 [US1] Implement POST create endpoint using create DTO without `clave` input in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [X] T024 [US1] Add validation error mapping for forbidden manual key field in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [X] T025 [US1] Update API contract for create operation and generated key response in specs/002-emp-composite-key/contracts/empleados-openapi.yaml

**Checkpoint**: User Story 1 is fully functional and independently testable (MVP)

---

## Phase 4: User Story 2 - Consulta por clave compuesta (Priority: P2)

**Goal**: Consultar y listar empleados con clave canónica validando formato de entrada.

**Independent Test**: Ejecutar consultas por clave válida e inválida y validar 200/400/404, además de listar empleados con claves `EMP-<número>`.

### Tests for User Story 2

- [X] T026 [P] [US2] Add contract test for GET list and GET by key endpoints in src/test/java/com/crudempleados/contract/EmpleadoReadContractTest.java
- [X] T027 [P] [US2] Add integration test for successful get by canonical key in src/test/java/com/crudempleados/integration/EmpleadoGetByCanonicalKeyIntegrationTest.java
- [X] T028 [P] [US2] Add integration test for invalid key format (400) in src/test/java/com/crudempleados/integration/EmpleadoInvalidKeyFormatIntegrationTest.java
- [X] T029 [P] [US2] Add integration test for non-existing key (404) in src/test/java/com/crudempleados/integration/EmpleadoNotFoundByKeyIntegrationTest.java

### Implementation for User Story 2

- [X] T030 [US2] Implement list and read-by-canonical-key queries in src/main/java/com/crudempleados/service/EmpleadoService.java
- [X] T031 [US2] Implement GET endpoints for list and key lookup in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [X] T032 [US2] Implement canonical key parser and invalid-format exception in src/main/java/com/crudempleados/exception/ClaveEmpleadoFormatoInvalidoException.java
- [X] T033 [US2] Wire 400 and 404 mapping for read operations in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [X] T034 [US2] Update API contract for read endpoints and key pattern in specs/002-emp-composite-key/contracts/empleados-openapi.yaml

**Checkpoint**: User Stories 1 and 2 work independently with canonical-key behavior

---

## Phase 5: User Story 3 - Transición de datos y operación CRUD completa (Priority: P3)

**Goal**: Migrar datos legados y mantener actualización/eliminación con clave compuesta sin pérdida de integridad.

**Independent Test**: Ejecutar migración de registros legados, actualizar y eliminar por clave canónica, y verificar consistencia de datos post-migración.

### Tests for User Story 3

- [X] T035 [P] [US3] Add migration integration test for legacy-key to canonical-key conversion in src/test/java/com/crudempleados/integration/EmpleadoLegacyKeyMigrationIntegrationTest.java
- [X] T036 [P] [US3] Add integration test for update by canonical key preserving key identity in src/test/java/com/crudempleados/integration/EmpleadoUpdateByCanonicalKeyIntegrationTest.java
- [X] T037 [P] [US3] Add integration test for delete by canonical key and repeated delete (404) in src/test/java/com/crudempleados/integration/EmpleadoDeleteByCanonicalKeyIntegrationTest.java
- [X] T038 [P] [US3] Add contract test for PUT/DELETE canonical key endpoints in src/test/java/com/crudempleados/contract/EmpleadoUpdateDeleteContractTest.java

### Implementation for User Story 3

- [X] T039 [US3] Implement migration execution/reporting support for legacy records in src/main/java/com/crudempleados/service/EmpleadoClaveMigrationService.java
- [X] T040 [US3] Implement update and delete by canonical key in src/main/java/com/crudempleados/service/EmpleadoService.java
- [X] T041 [US3] Implement PUT and DELETE handlers for canonical key path in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [X] T042 [US3] Add not-found and migration-failure mapping in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [X] T043 [US3] Update OpenAPI for update/delete and migration-related error semantics in specs/002-emp-composite-key/contracts/empleados-openapi.yaml

**Checkpoint**: All user stories are independently functional with migration compatibility

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cross-story quality hardening and final verification

- [X] T044 [P] Add focused unit tests for key formatter/parser and generator service in src/test/java/com/crudempleados/unit/ClaveEmpleadoServicesUnitTest.java
- [X] T045 Validate Docker startup and migration behavior against quickstart in specs/002-emp-composite-key/quickstart.md and docker/docker-compose.yml
- [X] T046 Validate OpenAPI accuracy against implemented endpoints in specs/002-emp-composite-key/contracts/empleados-openapi.yaml
- [X] T047 Validate security behavior and non-sensitive auth logging in src/main/java/com/crudempleados/config/SecurityConfig.java and src/main/resources/application.yml
- [X] T048 [P] Update implementation notes and usage examples for canonical key format in README.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: no dependencies, starts immediately
- **Foundational (Phase 2)**: depends on Phase 1 and blocks all user stories
- **User Story Phases (3-5)**: depend on Phase 2 completion
- **Polish (Phase 6)**: depends on selected story completion

### User Story Dependencies

- **US1 (P1)**: starts after Foundational and delivers MVP value
- **US2 (P2)**: starts after Foundational; independent from US1 except shared infrastructure
- **US3 (P3)**: starts after Foundational; depends on migration artifacts and canonical-key operations

### Within Each User Story

- Tests are written first and expected to fail initially
- Service/domain logic before controller handlers
- Controller handlers before final OpenAPI contract sync
- Story checkpoint validation before advancing

### Parallel Opportunities

- Setup: T003, T004, T005 can run in parallel
- Foundational: T009, T010, T011, T014, T015, T016 can run in parallel
- US1 tests: T017, T018, T019, T020 can run in parallel
- US2 tests: T026, T027, T028, T029 can run in parallel
- US3 tests: T035, T036, T037, T038 can run in parallel
- Polish: T044 and T048 can run in parallel

---

## Parallel Example: User Story 1

```bash
Task: "T017 Add contract test for POST /api/v1/empleados in src/test/java/com/crudempleados/contract/EmpleadoCreateContractTest.java"
Task: "T018 Add integration test for generated key format in src/test/java/com/crudempleados/integration/EmpleadoCreateGeneratedKeyIntegrationTest.java"
Task: "T020 Add concurrency integration test for unique key generation in src/test/java/com/crudempleados/integration/EmpleadoCreateConcurrentIntegrationTest.java"
```

## Parallel Example: User Story 3

```bash
Task: "T035 Add migration integration test in src/test/java/com/crudempleados/integration/EmpleadoLegacyKeyMigrationIntegrationTest.java"
Task: "T036 Add update-by-key integration test in src/test/java/com/crudempleados/integration/EmpleadoUpdateByCanonicalKeyIntegrationTest.java"
Task: "T037 Add delete-by-key integration test in src/test/java/com/crudempleados/integration/EmpleadoDeleteByCanonicalKeyIntegrationTest.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 only)

1. Complete Phase 1 and Phase 2
2. Deliver US1 (auto-generated canonical key)
3. Validate US1 independently and demo

### Incremental Delivery

1. Foundation ready (Phases 1-2)
2. Deliver US1 (create with generated key)
3. Deliver US2 (read/list with format validation)
4. Deliver US3 (migration + update/delete)
5. Execute Polish tasks and final validation

### Parallel Team Strategy

1. Team aligns on Phase 1 and 2
2. After foundational checkpoint:
   - Dev A: US1 (create + key generation)
   - Dev B: US2 (read + validation)
   - Dev C: US3 (migration + update/delete)
3. Merge per-story once each checkpoint passes

---

## Constitution Alignment

- Principle I (Spring Boot 3 + Java 17): covered by T001.
- Principle II (Mandatory HTTP Basic Authentication): covered by T014, T047.
- Principle III (PostgreSQL + migrations): covered by T007, T008, T039.
- Principle IV (Dockerized runtime and parity): covered by T003, T004, T045.
- Principle V (OpenAPI/Swagger contract sync): covered by T015, T025, T034, T043, T046.

---

## Notes

- [P] tasks target independent files to reduce merge conflicts
- [USx] labels preserve traceability to user stories
- Migration and key-generation tasks are explicit to reduce risk in identity transition
- Each story includes independent validation criteria before progression
