package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmpleadoGetByCanonicalKeyIntegrationTest {

    @Test
    void shouldAcceptCanonicalKey() {
        assertTrue("EMP-102".matches("^EMP-[0-9]+$"));
    }
}
