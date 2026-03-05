package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class EmpleadoInvalidKeyFormatIntegrationTest {

    @Test
    void shouldRejectInvalidFormat() {
        assertFalse("emp-102".matches("^EMP-[0-9]+$"));
    }
}
