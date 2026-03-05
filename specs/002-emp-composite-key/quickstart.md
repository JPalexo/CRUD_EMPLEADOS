# Quickstart - Clave Compuesta EMP

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

## 3) Levantar entorno local
1. Definir variables en `.env`.
2. Iniciar base de datos:
   - `docker compose up -d postgres`
3. Ejecutar aplicación:
   - `mvn spring-boot:run`

## 4) Ejecutar migraciones y validar transición
- Al inicio, la aplicación aplica migraciones versionadas.
- Verificar que empleados existentes queden con clave `EMP-<número>`.

## 5) Validar endpoints principales
Base URL: `http://localhost:8080/api/v1`

- Crear empleado (sin `clave` en request):
  - `POST /empleados`
- Listar empleados:
  - `GET /empleados`
- Consultar por clave compuesta:
  - `GET /empleados/{clave}` (ej. `EMP-102`)
- Actualizar por clave compuesta:
  - `PUT /empleados/{clave}`
- Eliminar por clave compuesta:
  - `DELETE /empleados/{clave}`

## 6) Casos de validación rápida
- Crear empleado y comprobar `clave` retornada con prefijo `EMP-`.
- Enviar `clave` manual en alta: debe fallar (400).
- Consultar `EMP-ABC` o `emp-10`: debe fallar (400).
- Consultar `EMP-999999` inexistente: debe retornar 404.

## 7) Swagger/OpenAPI
- UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`

## 8) Pruebas
- `mvn test`
- `mvn -Dtest=*IntegrationTest test`
