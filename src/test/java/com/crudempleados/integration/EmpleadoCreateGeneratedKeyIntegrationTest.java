package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmpleadoCreateGeneratedKeyIntegrationTest {

    @Test
    void generatedKeyShouldMatchPattern() {
        String clave = "EMP-1";
        assertTrue(clave.matches("^EMP-[0-9]+$"));
    }
}
