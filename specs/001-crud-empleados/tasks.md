# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/001-crud-empleados/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de pruebas porque la especificación exige escenarios verificables y la constitución requiere evidencia automatizada (unitarias e integración donde aplique).

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and basic structure

- [ ] T001 Initialize Spring Boot 3 + Java 17 project with Maven in pom.xml
- [ ] T002 Configure base application properties and environment placeholders in src/main/resources/application.yml
- [ ] T003 [P] Add Docker image definition for backend runtime in docker/Dockerfile
- [ ] T004 [P] Add local orchestration for app + PostgreSQL in docker/docker-compose.yml
- [ ] T005 [P] Add environment variable template for credentials and DB settings in .env.example
- [ ] T006 Add initial package structure and starter class in src/main/java/com/crudempleados/CrudEmpleadosApplication.java

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Core infrastructure that MUST be complete before ANY user story can be implemented

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [ ] T007 Add Flyway baseline migration for empleados table in src/main/resources/db/migration/V1__create_empleados_table.sql
- [ ] T008 [P] Implement JPA entity for Empleado with schema constraints in src/main/java/com/crudempleados/model/EmpleadoEntity.java
- [ ] T009 [P] Implement repository abstraction for Empleado persistence in src/main/java/com/crudempleados/repository/EmpleadoRepository.java
- [ ] T010 [P] Implement request/response DTOs with Bean Validation in src/main/java/com/crudempleados/dto/EmpleadoRequest.java and src/main/java/com/crudempleados/dto/EmpleadoResponse.java
- [ ] T011 Implement HTTP Basic security configuration with env-based credentials in src/main/java/com/crudempleados/config/SecurityConfig.java
- [ ] T012 [P] Implement OpenAPI security scheme and API metadata configuration in src/main/java/com/crudempleados/config/OpenApiConfig.java
- [ ] T013 Implement global exception mapping for 400/401/404/409 responses in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [ ] T014 [P] Add structured security event logging setup in src/main/resources/application.yml

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Registrar empleados (Priority: P1) 🎯 MVP

**Goal**: Permitir altas de empleados autenticadas con validación de campos y rechazo de duplicados por clave.

**Independent Test**: Crear un empleado válido con autenticación, verificar persistencia, validar rechazo por >100 caracteres y rechazo por clave duplicada.

### Tests for User Story 1

- [ ] T015 [P] [US1] Add contract test for POST /api/v1/empleados in src/test/java/com/crudempleados/contract/EmpleadoCreateContractTest.java
- [ ] T016 [P] [US1] Add integration test for successful create and duplicate clave in src/test/java/com/crudempleados/integration/EmpleadoCreateIntegrationTest.java
- [ ] T017 [P] [US1] Add integration test for validation length >100 in src/test/java/com/crudempleados/integration/EmpleadoValidationIntegrationTest.java

### Implementation for User Story 1

- [ ] T018 [US1] Implement create command in service layer in src/main/java/com/crudempleados/service/EmpleadoService.java
- [ ] T019 [US1] Implement POST create endpoint in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [ ] T020 [US1] Implement duplicate clave domain exception in src/main/java/com/crudempleados/exception/EmpleadoDuplicadoException.java
- [ ] T021 [US1] Wire 409 conflict mapping for duplicate create in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [ ] T022 [US1] Update OpenAPI contract for create responses and validation errors in specs/001-crud-empleados/contracts/empleados-openapi.yaml

**Checkpoint**: At this point, User Story 1 should be fully functional and testable independently

---

## Phase 4: User Story 2 - Consultar empleados (Priority: P2)

**Goal**: Permitir listado completo y consulta puntual por clave para usuarios autenticados.

**Independent Test**: Con datos existentes, validar GET de listado, GET por clave existente y 404 por clave inexistente.

### Tests for User Story 2

- [ ] T023 [P] [US2] Add contract test for GET /api/v1/empleados and GET /api/v1/empleados/{clave} in src/test/java/com/crudempleados/contract/EmpleadoReadContractTest.java
- [ ] T024 [P] [US2] Add integration test for list and get-by-clave success in src/test/java/com/crudempleados/integration/EmpleadoReadIntegrationTest.java
- [ ] T025 [P] [US2] Add integration test for get-by-clave not found in src/test/java/com/crudempleados/integration/EmpleadoNotFoundIntegrationTest.java

### Implementation for User Story 2

- [ ] T026 [US2] Implement list and get-by-clave queries in src/main/java/com/crudempleados/service/EmpleadoService.java
- [ ] T027 [US2] Implement GET list and GET by clave endpoints in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [ ] T028 [US2] Implement not-found domain exception for read operations in src/main/java/com/crudempleados/exception/EmpleadoNoEncontradoException.java
- [ ] T029 [US2] Wire 404 mapping for read not-found cases in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [ ] T030 [US2] Update OpenAPI contract for read endpoints and 404 responses in specs/001-crud-empleados/contracts/empleados-openapi.yaml

**Checkpoint**: At this point, User Stories 1 AND 2 should both work independently

---

## Phase 5: User Story 3 - Actualizar y eliminar empleados (Priority: P3)

**Goal**: Permitir edición y baja de empleados existentes, con manejo correcto de no encontrados.

**Independent Test**: Actualizar y eliminar un empleado existente y confirmar 404 cuando la clave no existe o se elimina dos veces.

### Tests for User Story 3

- [ ] T031 [P] [US3] Add contract test for PUT and DELETE /api/v1/empleados/{clave} in src/test/java/com/crudempleados/contract/EmpleadoUpdateDeleteContractTest.java
- [ ] T032 [P] [US3] Add integration test for successful update flow in src/test/java/com/crudempleados/integration/EmpleadoUpdateIntegrationTest.java
- [ ] T033 [P] [US3] Add integration test for successful delete and repeated delete not found in src/test/java/com/crudempleados/integration/EmpleadoDeleteIntegrationTest.java

### Implementation for User Story 3

- [ ] T034 [US3] Implement update and delete operations in src/main/java/com/crudempleados/service/EmpleadoService.java
- [ ] T035 [US3] Implement PUT and DELETE endpoints in src/main/java/com/crudempleados/controller/EmpleadoController.java
- [ ] T036 [US3] Reuse not-found exception handling for update/delete in src/main/java/com/crudempleados/exception/GlobalExceptionHandler.java
- [ ] T037 [US3] Enforce update input validation constraints in src/main/java/com/crudempleados/dto/EmpleadoRequest.java
- [ ] T038 [US3] Update OpenAPI contract for update/delete status codes in specs/001-crud-empleados/contracts/empleados-openapi.yaml

**Checkpoint**: All user stories should now be independently functional

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Improvements that affect multiple user stories

- [ ] T039 [P] Add focused unit tests for service edge cases in src/test/java/com/crudempleados/unit/EmpleadoServiceTest.java
- [ ] T040 Verify Docker workflow and documented startup commands in specs/001-crud-empleados/quickstart.md and docker/docker-compose.yml
- [ ] T041 Verify Swagger accuracy against implemented endpoints in specs/001-crud-empleados/contracts/empleados-openapi.yaml
- [ ] T042 Validate authentication failure logging without secrets in src/main/resources/application.yml and src/main/java/com/crudempleados/config/SecurityConfig.java
- [ ] T043 [P] Document final implementation notes and API usage examples in README.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: All depend on Foundational phase completion
  - Recommended order by priority: US1 → US2 → US3
  - US2 and US3 can proceed in parallel after foundation if team capacity allows
- **Polish (Phase 6)**: Depends on all selected user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Depends only on Foundational phase
- **User Story 2 (P2)**: Depends on Foundational phase and existing persisted empleados for independent validation
- **User Story 3 (P3)**: Depends on Foundational phase and existing empleados to update/delete

### Within Each User Story

- Tests MUST be created before implementation and should fail initially
- Service logic before controller wiring
- Controller wiring before OpenAPI contract final sync
- Story checkpoint validation before moving to next priority

### Dependency Graph (Story Completion Order)

- Foundation → US1 (MVP)
- Foundation → US2
- Foundation → US3
- US1, US2, US3 → Polish

### Parallel Opportunities

- Setup: T003, T004, T005 can run in parallel after T001/T002
- Foundational: T008, T009, T010, T012, T014 can run in parallel
- US1 tests: T015, T016, T017 can run in parallel
- US2 tests: T023, T024, T025 can run in parallel
- US3 tests: T031, T032, T033 can run in parallel
- Polish: T039 and T043 can run in parallel

---

## Parallel Example: User Story 1

```bash
# Run US1 test authoring in parallel:
Task: "T015 Add contract test for POST /api/v1/empleados in src/test/java/com/crudempleados/contract/EmpleadoCreateContractTest.java"
Task: "T016 Add integration test for successful create and duplicate clave in src/test/java/com/crudempleados/integration/EmpleadoCreateIntegrationTest.java"
Task: "T017 Add integration test for validation length >100 in src/test/java/com/crudempleados/integration/EmpleadoValidationIntegrationTest.java"
```

## Parallel Example: User Story 2

```bash
# Run US2 test authoring in parallel:
Task: "T023 Add contract test for GET endpoints in src/test/java/com/crudempleados/contract/EmpleadoReadContractTest.java"
Task: "T024 Add integration test for read success in src/test/java/com/crudempleados/integration/EmpleadoReadIntegrationTest.java"
Task: "T025 Add integration test for read not found in src/test/java/com/crudempleados/integration/EmpleadoNotFoundIntegrationTest.java"
```

## Parallel Example: User Story 3

```bash
# Run US3 test authoring in parallel:
Task: "T031 Add contract test for PUT/DELETE endpoints in src/test/java/com/crudempleados/contract/EmpleadoUpdateDeleteContractTest.java"
Task: "T032 Add integration test for update success in src/test/java/com/crudempleados/integration/EmpleadoUpdateIntegrationTest.java"
Task: "T033 Add integration test for delete and repeated delete not found in src/test/java/com/crudempleados/integration/EmpleadoDeleteIntegrationTest.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational
3. Complete Phase 3: User Story 1
4. Validate US1 independently as MVP

### Incremental Delivery

1. Setup + Foundational
2. Deliver US1 (create)
3. Deliver US2 (read)
4. Deliver US3 (update/delete)
5. Run Polish tasks and final verification

### Parallel Team Strategy

1. Team completes Setup + Foundational
2. Then split by story or by test/implementation tracks per story
3. Merge each story after passing its independent checkpoint

---

## Notes

- [P] tasks are designed for different files and no direct dependency on incomplete tasks
- [USx] labels provide traceability from tasks to user stories
- Each story has an explicit independent test criterion and completion checkpoint
- Task descriptions include explicit file paths for immediate execution
