package com.crudempleados.exception;

public class ClaveEmpleadoFormatoInvalidoException extends RuntimeException {

    public ClaveEmpleadoFormatoInvalidoException(String clave) {
        super("Formato de clave inválido: " + clave);
    }
}
