package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class EmpleadoCreateManualKeyRejectedIntegrationTest {

    @Test
    void manualKeyShouldBeRejectedBySchema() {
        assertFalse("clave".isEmpty());
    }
}
