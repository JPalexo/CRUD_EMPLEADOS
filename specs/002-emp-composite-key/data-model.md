# Data Model - Clave Compuesta EMP

## Entity: Empleado

### Description
Registro de empleado con identidad canónica de negocio `clave` en formato `EMP-<consecutivo>`.

### Fields
- `clave` (string, required): Identificador canónico expuesto por API (`EMP-<número>`).
- `clave_prefijo` (string, required, fixed): Segmento fijo `EMP-`.
- `clave_consecutivo` (integer, required, positive, unique): Segmento numérico autogenerado no reutilizable.
- `nombre` (string, required, max 100)
- `direccion` (string, required, max 100)
- `telefono` (string, required, max 100)
- `created_at` (timestamp, required)
- `updated_at` (timestamp, required)

### Validation Rules
- `clave` MUST match `^EMP-[0-9]+$` cuando sea recibida en rutas.
- `clave_prefijo` MUST be exactly `EMP-`.
- `clave_consecutivo` MUST be `> 0`, unique y no reutilizable.
- `nombre`, `direccion`, `telefono` MUST be non-empty and length `<= 100`.
- Create request MUST NOT accept client-provided `clave`.

### Persistence Mapping (PostgreSQL)
- Table: `empleados`
- Columns:
  - `clave_prefijo` VARCHAR(4) NOT NULL DEFAULT 'EMP-'
  - `clave_consecutivo` BIGINT NOT NULL UNIQUE
  - `nombre` VARCHAR(100) NOT NULL
  - `direccion` VARCHAR(100) NOT NULL
  - `telefono` VARCHAR(100) NOT NULL
  - `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  - `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
- Primary key: (`clave_prefijo`, `clave_consecutivo`)
- Generated API key representation: concatenación `clave_prefijo || clave_consecutivo`

## Entity: RegistroMigracionClave

### Description
Resultado auditable de la migración de claves legadas al nuevo formato compuesto.

### Fields
- `empleado_origen` (reference, required)
- `clave_anterior` (string/integer textual, required)
- `clave_nueva` (string, required, format `EMP-<número>`)
- `estado` (enum, required: `MIGRATED`, `SKIPPED`, `FAILED`)
- `detalle` (string, optional)
- `fecha_ejecucion` (timestamp, required)

### Transition Rules
- Migración debe ser transaccional por lote y dejar consistencia referencial completa.
- Si hay fallo crítico, MUST permitir rollback completo del lote.
