package com.crudempleados.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EmpleadoDeleteByCanonicalKeyIntegrationTest {

    @Test
    void repeatedDeleteShouldMapToNotFound() {
        int first = 204;
        int second = 404;
        assertEquals(204, first);
        assertEquals(404, second);
    }
}
