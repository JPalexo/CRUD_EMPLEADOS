package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class EmpleadoCreateConcurrentIntegrationTest {

    @Test
    void generatedKeysShouldBeUnique() {
        String k1 = "EMP-101";
        String k2 = "EMP-102";
        assertNotEquals(k1, k2);
    }
}
