package com.crudempleados.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmpleadoId implements Serializable {

    @Column(name = "clave_prefijo", nullable = false, length = 4)
    private String clavePrefijo;

    @Column(name = "clave_consecutivo", nullable = false)
    private Long claveConsecutivo;

    public EmpleadoId() {
    }

    public EmpleadoId(String clavePrefijo, Long claveConsecutivo) {
        this.clavePrefijo = clavePrefijo;
        this.claveConsecutivo = claveConsecutivo;
    }

    public String getClavePrefijo() {
        return clavePrefijo;
    }

    public void setClavePrefijo(String clavePrefijo) {
        this.clavePrefijo = clavePrefijo;
    }

    public Long getClaveConsecutivo() {
        return claveConsecutivo;
    }

    public void setClaveConsecutivo(Long claveConsecutivo) {
        this.claveConsecutivo = claveConsecutivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpleadoId empleadoId)) {
            return false;
        }
        return Objects.equals(clavePrefijo, empleadoId.clavePrefijo)
            && Objects.equals(claveConsecutivo, empleadoId.claveConsecutivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clavePrefijo, claveConsecutivo);
    }
}
