package com.crudempleados.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(String clave) {
        super("Empleado no encontrado: " + clave);
    }
}
