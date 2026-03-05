package com.crudempleados.integration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crudempleados.config.SecurityConfig;
import com.crudempleados.exception.ClaveEmpleadoFormatoInvalidoException;
import com.crudempleados.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
    "app.security.username=test-user",
    "app.security.password=test-pass"
})
class EmpleadoSecurityAndValidationWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void shouldRejectUnauthorizedRequests() throws Exception {
        mockMvc.perform(get("/api/v1/empleados"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnBadRequestForInvalidCanonicalKey() throws Exception {
        given(empleadoService.findByClave(anyString()))
            .willThrow(new ClaveEmpleadoFormatoInvalidoException("emp-1"));

        mockMvc.perform(get("/api/v1/empleados/emp-1").with(httpBasic("test-user", "test-pass")))
            .andExpect(status().isBadRequest());
    }
}
