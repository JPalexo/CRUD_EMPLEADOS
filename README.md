# CRUD Empleados

Servicio backend Spring Boot 3 + Java 17 para CRUD de empleados con clave canónica `EMP-<consecutivo>`.

## Arranque local

1. Copiar `.env.example` a `.env` y ajustar valores.
2. Levantar Postgres:
   - `docker compose -f docker/docker-compose.yml up -d postgres`
3. Ejecutar app:
   - `mvn spring-boot:run`

## Endpoints

- `POST /api/v1/empleados`
- `GET /api/v1/empleados`
- `GET /api/v1/empleados/{clave}`
- `PUT /api/v1/empleados/{clave}`
- `DELETE /api/v1/empleados/{clave}`

## Seguridad

- HTTP Basic para endpoints de negocio.
- En `dev` pueden exponerse `/actuator/health`, `/swagger-ui/**`, `/v3/api-docs/**`.

## Swagger

- `http://localhost:8080/swagger-ui/index.html`
