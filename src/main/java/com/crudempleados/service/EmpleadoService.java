package com.crudempleados.service;

import com.crudempleados.dto.EmpleadoCreateRequest;
import com.crudempleados.dto.EmpleadoResponse;
import com.crudempleados.dto.EmpleadoUpdateRequest;
import com.crudempleados.exception.EmpleadoNoEncontradoException;
import com.crudempleados.model.EmpleadoEntity;
import com.crudempleados.model.EmpleadoId;
import com.crudempleados.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final ClaveEmpleadoGeneratorService claveEmpleadoGeneratorService;
    private final ClaveEmpleadoFormatter claveEmpleadoFormatter;

    public EmpleadoService(
        EmpleadoRepository empleadoRepository,
        ClaveEmpleadoGeneratorService claveEmpleadoGeneratorService,
        ClaveEmpleadoFormatter claveEmpleadoFormatter
    ) {
        this.empleadoRepository = empleadoRepository;
        this.claveEmpleadoGeneratorService = claveEmpleadoGeneratorService;
        this.claveEmpleadoFormatter = claveEmpleadoFormatter;
    }

    @Transactional
    public EmpleadoResponse create(EmpleadoCreateRequest request) {
        long consecutivo = claveEmpleadoGeneratorService.nextConsecutivo();
        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setId(new EmpleadoId(ClaveEmpleadoFormatter.PREFIX, consecutivo));
        entity.setNombre(request.getNombre());
        entity.setDireccion(request.getDireccion());
        entity.setTelefono(request.getTelefono());
        EmpleadoEntity saved = empleadoRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<EmpleadoResponse> list(int page, int size) {
        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by("id.claveConsecutivo").ascending()
        );
        return empleadoRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse findByClave(String clave) {
        EmpleadoId id = claveEmpleadoFormatter.parse(clave);
        EmpleadoEntity entity = empleadoRepository.findById(id)
            .orElseThrow(() -> new EmpleadoNoEncontradoException(clave));
        return toResponse(entity);
    }

    @Transactional
    public EmpleadoResponse update(String clave, EmpleadoUpdateRequest request) {
        EmpleadoId id = claveEmpleadoFormatter.parse(clave);
        EmpleadoEntity entity = empleadoRepository.findById(id)
            .orElseThrow(() -> new EmpleadoNoEncontradoException(clave));
        entity.setNombre(request.getNombre());
        entity.setDireccion(request.getDireccion());
        entity.setTelefono(request.getTelefono());
        return toResponse(empleadoRepository.save(entity));
    }

    @Transactional
    public void delete(String clave) {
        EmpleadoId id = claveEmpleadoFormatter.parse(clave);
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException(clave);
        }
        empleadoRepository.deleteById(id);
    }

    private EmpleadoResponse toResponse(EmpleadoEntity entity) {
        return new EmpleadoResponse(
            claveEmpleadoFormatter.format(entity.getId()),
            entity.getNombre(),
            entity.getDireccion(),
            entity.getTelefono()
        );
    }
}
