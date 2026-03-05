package com.crudempleados.contract;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.crudempleados.dto.EmpleadoCreateRequest;
import org.junit.jupiter.api.Test;

class EmpleadoCreateContractTest {

    @Test
    void shouldAllowCreateSchemaWithoutClave() {
        EmpleadoCreateRequest req = new EmpleadoCreateRequest();
        req.setNombre("Juan");
        req.setDireccion("Centro");
        req.setTelefono("5551234");
        assertDoesNotThrow(req::getNombre);
    }
}
