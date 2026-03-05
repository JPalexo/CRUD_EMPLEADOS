package com.crudempleados.repository;

import com.crudempleados.model.EmpleadoEntity;
import com.crudempleados.model.EmpleadoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, EmpleadoId> {
}
