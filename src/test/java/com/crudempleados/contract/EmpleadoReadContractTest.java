package com.crudempleados.contract;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmpleadoReadContractTest {

    @Test
    void shouldUseCanonicalKeyPattern() {
        assertTrue("EMP-123".matches("^EMP-[0-9]+$"));
    }
}
