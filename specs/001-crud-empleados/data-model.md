# Data Model - CRUD de Empleados

## Entity: Empleado

### Description
Representa un registro del catálogo de empleados administrado por usuarios autenticados.

### Fields
- `clave` (integer, required, positive): Identificador primario del empleado.
- `nombre` (string, required, max 100): Nombre del empleado.
- `direccion` (string, required, max 100): Dirección del empleado.
- `telefono` (string, required, max 100): Teléfono del empleado.
- `created_at` (timestamp, required): Fecha/hora de creación del registro (auditoría técnica).
- `updated_at` (timestamp, required): Fecha/hora de última modificación (auditoría técnica).

### Validation Rules
- `clave` MUST be unique and MUST be the primary key.
- `clave` MUST be `> 0`.
- `nombre`, `direccion`, `telefono` MUST NOT be null/blank.
- `nombre`, `direccion`, `telefono` MUST have length `<= 100`.
- Create operation with duplicate `clave` MUST fail with conflict.

### Persistence Mapping (PostgreSQL)
- Table: `empleados`
- Columns:
  - `clave` INTEGER PRIMARY KEY
  - `nombre` VARCHAR(100) NOT NULL
  - `direccion` VARCHAR(100) NOT NULL
  - `telefono` VARCHAR(100) NOT NULL
  - `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  - `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

### State Transitions
- `CREATED`: Registro dado de alta mediante POST.
- `UPDATED`: Registro modificado mediante PUT.
- `DELETED`: Registro eliminado físicamente mediante DELETE.

### Notes
- No hay estado de negocio intermedio para empleados; el ciclo es estrictamente CRUD.
- La eliminación repetida de una misma `clave` debe responder como no encontrado.
