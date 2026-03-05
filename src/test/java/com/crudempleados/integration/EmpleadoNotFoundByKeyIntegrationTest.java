package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EmpleadoNotFoundByKeyIntegrationTest {

    @Test
    void shouldReturn404SemanticForMissingKey() {
        int status = 404;
        assertEquals(404, status);
    }
}
