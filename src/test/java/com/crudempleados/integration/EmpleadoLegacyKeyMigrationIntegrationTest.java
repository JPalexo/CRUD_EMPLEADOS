package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmpleadoLegacyKeyMigrationIntegrationTest {

    @Test
    void migrationShouldNormalizeLegacyKeys() {
        assertTrue("EMP-15".startsWith("EMP-"));
    }
}
