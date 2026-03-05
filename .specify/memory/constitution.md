<!--
Sync Impact Report
- Version change: 0.0.0 → 1.0.0
- Modified principles:
	- [PRINCIPLE_1_NAME] → I. Backend-First with Spring Boot 3 + Java 17
	- [PRINCIPLE_2_NAME] → II. Mandatory HTTP Basic Authentication
	- [PRINCIPLE_3_NAME] → III. PostgreSQL as Source of Truth
	- [PRINCIPLE_4_NAME] → IV. Dockerized Runtime and Local Parity
	- [PRINCIPLE_5_NAME] → V. API Contract and Swagger Documentation
- Added sections:
	- Security and API Standards
	- Delivery Workflow and Quality Gates
- Removed sections:
	- None
- Templates requiring updates:
	- ✅ updated: .specify/templates/plan-template.md
	- ✅ updated: .specify/templates/spec-template.md
	- ✅ updated: .specify/templates/tasks-template.md
	- ⚠ pending: .specify/templates/commands/*.md (directory not present)
- Follow-up TODOs:
	- None
-->

# CRUD Empleados Constitution

## Core Principles

### I. Backend-First with Spring Boot 3 + Java 17
All backend services MUST be implemented with Spring Boot 3 and Java 17.
Alternative backend frameworks or Java versions MUST NOT be introduced without a
constitution amendment. Shared architecture MUST follow controller, service,
repository layering with clear responsibility boundaries.

Rationale: A single modern stack reduces operational risk, accelerates onboarding,
and simplifies maintenance.

### II. Mandatory HTTP Basic Authentication
Every exposed API endpoint (except health and documentation endpoints explicitly
approved in the spec) MUST require HTTP Basic Authentication implemented through
Spring Security. Credentials MUST be externalized via environment variables or
secrets and MUST NOT be hard-coded.

Rationale: The project requires a simple, auditable, and consistent security
baseline for all protected operations.

### III. PostgreSQL as Source of Truth
Persistent data MUST be stored in PostgreSQL. Schema changes MUST be versioned via
migrations and applied deterministically across environments. Application code MUST
use typed entities and repository abstractions; direct ad hoc SQL in controllers is
prohibited.

Rationale: PostgreSQL provides reliability and transactional integrity aligned with
backend CRUD workloads.

### IV. Dockerized Runtime and Local Parity
The backend and PostgreSQL runtime MUST be executable via Docker with reproducible
configuration. Local development and CI MUST use equivalent containerized services
to avoid environment drift. Compose-based orchestration SHOULD be used for local
multi-service execution.

Rationale: Container parity minimizes “works on my machine” defects and improves
deployment predictability.

### V. API Contract and Swagger Documentation
Every public REST endpoint MUST be documented through OpenAPI/Swagger and kept in
sync with implementation changes in the same delivery scope. Request/response
models, authentication requirements, and error codes MUST be explicit.

Rationale: Accurate API contracts enable safe integration and reduce regressions.

## Security and API Standards

- API base path MUST be versioned (for example, `/api/v1`).
- Security-sensitive events (authentication failures, forbidden access, startup
	config validation failures) MUST be logged with structured, non-sensitive data.
- Passwords, tokens, and secrets MUST NEVER be written to logs.
- Input validation MUST be enforced at API boundaries with explicit error responses.
- Swagger UI exposure in production MUST be controlled through environment-specific
	configuration.

## Delivery Workflow and Quality Gates

- Every feature spec MUST confirm stack compliance: Spring Boot 3, Java 17,
	PostgreSQL, Docker, and Swagger.
- Every implementation plan MUST include constitution gates for authentication,
	persistence, containerization, and API documentation.
- Pull requests MUST include:
	- automated test results (unit and integration where applicable),
	- migration impact notes for database changes,
	- verification that Swagger is updated for contract changes.
- A change that violates any Core Principle MUST NOT be merged without a prior
	constitution amendment.

## Governance

This constitution is the highest-priority engineering policy for this repository.
If a lower-level document conflicts with this constitution, this constitution takes
precedence.

Amendment process:
1. Propose changes in a dedicated constitution update.
2. Document impacted principles, migration impact, and template sync changes.
3. Obtain approval from project maintainers before implementation proceeds.

Versioning policy (semantic versioning):
- MAJOR: Backward-incompatible governance changes or principle removals/redefinitions.
- MINOR: New principle/section or materially expanded mandatory guidance.
- PATCH: Clarifications, wording improvements, typo/non-semantic edits.

Compliance review expectations:
- Plan, spec, and tasks artifacts MUST include explicit constitution alignment.
- Reviews MUST reject PRs lacking evidence for security, persistence, Docker, or
	Swagger compliance when those concerns are in scope.
- Exceptions MUST be time-boxed, documented, and tracked with owners.

**Version**: 1.0.0 | **Ratified**: 2026-02-26 | **Last Amended**: 2026-02-26
