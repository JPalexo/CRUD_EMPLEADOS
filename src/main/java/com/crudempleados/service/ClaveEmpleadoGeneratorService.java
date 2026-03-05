package com.crudempleados.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClaveEmpleadoGeneratorService {

    private final JdbcTemplate jdbcTemplate;

    public ClaveEmpleadoGeneratorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long nextConsecutivo() {
        Long next = jdbcTemplate.queryForObject("SELECT nextval('empleado_clave_seq')", Long.class);
        if (next == null || next <= 0) {
            throw new IllegalStateException("No fue posible generar consecutivo de clave");
        }
        return next;
    }
}
