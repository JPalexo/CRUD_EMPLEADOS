# Quickstart - CRUD de Empleados

## 1) Prerrequisitos
- Java 17
- Maven 3.9+
- Docker + Docker Compose

## 2) Variables de entorno mínimas
- `APP_BASIC_AUTH_USER`
- `APP_BASIC_AUTH_PASSWORD`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## 3) Levantar entorno local con Docker Compose
1. Definir variables en `.env`.
2. Ejecutar:
   - `docker compose up -d postgres`
3. Arrancar la aplicación:
   - `mvn spring-boot:run`

## 4) Verificar endpoints principales
Base URL: `http://localhost:8080/api/v1`

- Crear empleado:
  - `POST /empleados`
- Listar empleados:
  - `GET /empleados`
- Consultar por clave:
  - `GET /empleados/{clave}`
- Actualizar empleado:
  - `PUT /empleados/{clave}`
- Eliminar empleado:
  - `DELETE /empleados/{clave}`

Todas las operaciones CRUD requieren HTTP Basic Authentication.

## 5) Ver Swagger/OpenAPI
- UI: `http://localhost:8080/swagger-ui.html` o `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 6) Ejecutar pruebas
- `mvn test`
- (Integración con contenedor) `mvn -Dtest=*IntegrationTest test`

## 7) Casos de validación rápida
- Campos de texto con 100 caracteres: válido.
- Campos de texto con 101 caracteres: `400 Bad Request`.
- `clave` inexistente en GET/PUT/DELETE: `404 Not Found`.
- `clave` duplicada en POST: `409 Conflict`.
