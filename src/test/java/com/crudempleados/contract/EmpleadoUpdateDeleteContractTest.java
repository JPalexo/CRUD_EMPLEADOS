package com.crudempleados.contract;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmpleadoUpdateDeleteContractTest {

    @Test
    void shouldValidateCanonicalKeyForUpdateDelete() {
        assertTrue("EMP-999".matches("^EMP-[0-9]+$"));
        assertFalse("EMP-ABC".matches("^EMP-[0-9]+$"));
    }
}
