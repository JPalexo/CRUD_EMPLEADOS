# Phase 0 Research - CRUD de Empleados

## Decision 1: API style and versioning
- Decision: Expose REST endpoints under `/api/v1/empleados` with JSON request/response DTOs.
- Rationale: Aligns with constitution requirement for versioned base path and keeps contract clear for CRUD workflows.
- Alternatives considered:
  - Unversioned `/empleados`: rejected because it breaks API versioning policy.
  - RPC-style endpoints (`/createEmpleado`, `/updateEmpleado`): rejected for lower REST consistency and weaker evolvability.

## Decision 2: Authentication mechanism
- Decision: Use Spring Security HTTP Basic Authentication for all CRUD endpoints; allow health/doc endpoints by explicit config.
- Rationale: Constitution mandates Basic Auth and this is sufficient for internal administrative use.
- Alternatives considered:
  - JWT/OAuth2: rejected as unnecessary complexity for current scope.
  - Custom auth filter: rejected due to maintainability and higher security risk.

## Decision 3: Credential sourcing
- Decision: Read auth credentials from environment variables (e.g., `APP_BASIC_AUTH_USER`, `APP_BASIC_AUTH_PASSWORD`) and map through Spring configuration.
- Rationale: Satisfies constitution requirement to externalize secrets and prevents hardcoded credentials.
- Alternatives considered:
  - Credentials in `application.yml`: rejected for secret exposure risk.
  - In-memory fixed credentials in code: rejected by constitution.

## Decision 4: Persistence and migration strategy
- Decision: Use PostgreSQL as primary database with Flyway SQL migrations in `src/main/resources/db/migration`.
- Rationale: Constitution requires PostgreSQL and deterministic versioned migrations.
- Alternatives considered:
  - Hibernate `ddl-auto` schema generation: rejected because schema changes are not explicitly version-controlled.
  - Liquibase: viable alternative, rejected to keep SQL-first migration simplicity for this scope.

## Decision 5: Domain modeling for empleado
- Decision: Model `Empleado` entity with `clave` as primary key and `nombre`, `direccion`, `telefono` as required fields (`VARCHAR(100)`).
- Rationale: Directly matches functional requirements and keeps API/data model aligned.
- Alternatives considered:
  - Surrogate ID + unique `clave`: rejected because requirement states `clave` as primary identifier.
  - Wider field lengths: rejected because requirement caps each to 100 chars.

## Decision 6: Validation and error mapping
- Decision: Enforce input constraints at DTO boundary using Bean Validation (`@NotBlank`, `@Size(max=100)`) and map errors to explicit 400/404/409 responses.
- Rationale: Supports required edge cases and explicit API contract behavior.
- Alternatives considered:
  - Manual validation in controllers: rejected for duplication and lower consistency.
  - DB-only constraints without API validation: rejected due to poor client error quality.

## Decision 7: API documentation approach
- Decision: Use `springdoc-openapi` to generate Swagger/OpenAPI and document security scheme (HTTP Basic), request/response models, and error codes.
- Rationale: Required by constitution and minimizes drift between implementation and docs.
- Alternatives considered:
  - Hand-written static OpenAPI only: rejected due to higher maintenance overhead.
  - No interactive docs: rejected by explicit requirement.

## Decision 8: Containerized local parity
- Decision: Use Docker Compose with two services (`app`, `postgres`) and environment-based wiring for DB/auth settings.
- Rationale: Constitution requires dockerized runtime and local parity for app + database.
- Alternatives considered:
  - Local DB without containers: rejected because it increases environment drift.
  - App-only containerization: rejected because parity requires both app and PostgreSQL runtime.

## Decision 9: Test strategy for quality gates
- Decision: Implement layered tests: unit (service/validation), integration (`@SpringBootTest` + Testcontainers PostgreSQL), and contract checks for endpoint auth/errors.
- Rationale: Covers constitution delivery gate expectations and key behavior risks.
- Alternatives considered:
  - Unit-only tests: rejected because DB and security integration behavior would be unverified.
  - Full end-to-end UI tests: rejected as out of scope for backend-only feature.

## Decision 10: Observability and safe logging
- Decision: Log security events (auth failures, forbidden access, startup validation failures) with structured non-sensitive metadata only.
- Rationale: Required by constitution security standards while avoiding secret leakage.
- Alternatives considered:
  - Verbose request/credential logging: rejected as security risk.
  - No security event logs: rejected due to auditability requirement.
