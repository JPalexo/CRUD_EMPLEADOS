CREATE TABLE IF NOT EXISTS empleados (
    clave_prefijo VARCHAR(4) NOT NULL DEFAULT 'EMP-',
    clave_consecutivo BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_empleados PRIMARY KEY (clave_prefijo, clave_consecutivo),
    CONSTRAINT uq_empleados_consecutivo UNIQUE (clave_consecutivo)
);
