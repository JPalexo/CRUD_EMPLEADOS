package com.crudempleados.controller;

import com.crudempleados.dto.EmpleadoCreateRequest;
import com.crudempleados.dto.EmpleadoResponse;
import com.crudempleados.dto.EmpleadoUpdateRequest;
import com.crudempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponse create(@Valid @RequestBody EmpleadoCreateRequest request) {
        return empleadoService.create(request);
    }

    @GetMapping
    public Page<EmpleadoResponse> list(
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page,
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "10") int size
    ) {
        return empleadoService.list(page, size);
    }

    @GetMapping("/{clave}")
    public EmpleadoResponse get(@PathVariable String clave) {
        return empleadoService.findByClave(clave);
    }

    @PutMapping("/{clave}")
    public EmpleadoResponse update(@PathVariable String clave, @Valid @RequestBody EmpleadoUpdateRequest request) {
        return empleadoService.update(clave, request);
    }

    @DeleteMapping("/{clave}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String clave) {
        empleadoService.delete(clave);
    }
}
