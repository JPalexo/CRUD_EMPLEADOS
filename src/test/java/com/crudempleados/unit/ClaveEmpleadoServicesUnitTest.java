package com.crudempleados.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.crudempleados.exception.ClaveEmpleadoFormatoInvalidoException;
import com.crudempleados.model.EmpleadoId;
import com.crudempleados.service.ClaveEmpleadoFormatter;
import org.junit.jupiter.api.Test;

class ClaveEmpleadoServicesUnitTest {

    private final ClaveEmpleadoFormatter formatter = new ClaveEmpleadoFormatter();

    @Test
    void shouldFormatAndParseCanonicalKey() {
        EmpleadoId id = new EmpleadoId("EMP-", 10L);
        String clave = formatter.format(id);
        assertEquals("EMP-10", clave);
        EmpleadoId parsed = formatter.parse(clave);
        assertEquals("EMP-", parsed.getClavePrefijo());
        assertEquals(10L, parsed.getClaveConsecutivo());
    }

    @Test
    void shouldRejectInvalidKeyFormat() {
        assertThrows(ClaveEmpleadoFormatoInvalidoException.class, () -> formatter.parse("emp-10"));
    }
}
