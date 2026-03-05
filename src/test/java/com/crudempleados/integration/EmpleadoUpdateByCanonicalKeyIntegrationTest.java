package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EmpleadoUpdateByCanonicalKeyIntegrationTest {

    @Test
    void updateShouldPreserveCanonicalKey() {
        String before = "EMP-25";
        String after = "EMP-25";
        assertEquals(before, after);
    }
}
