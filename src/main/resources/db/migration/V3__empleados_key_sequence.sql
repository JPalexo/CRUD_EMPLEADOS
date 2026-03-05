CREATE SEQUENCE IF NOT EXISTS empleado_clave_seq START WITH 1 INCREMENT BY 1;

SELECT setval(
    'empleado_clave_seq',
    COALESCE((SELECT MAX(clave_consecutivo) FROM empleados), 0) + 1,
    false
);
