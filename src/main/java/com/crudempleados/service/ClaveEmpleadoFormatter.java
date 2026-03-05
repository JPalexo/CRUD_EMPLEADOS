package com.crudempleados.service;

import com.crudempleados.exception.ClaveEmpleadoFormatoInvalidoException;
import com.crudempleados.model.EmpleadoId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class ClaveEmpleadoFormatter {

    public static final String PREFIX = "EMP-";
    private static final Pattern CLAVE_PATTERN = Pattern.compile("^EMP-([0-9]+)$");

    public String format(EmpleadoId id) {
        return PREFIX + id.getClaveConsecutivo();
    }

    public EmpleadoId parse(String clave) {
        Matcher matcher = CLAVE_PATTERN.matcher(clave == null ? "" : clave.trim());
        if (!matcher.matches()) {
            throw new ClaveEmpleadoFormatoInvalidoException(clave);
        }
        long consecutivo = Long.parseLong(matcher.group(1));
        if (consecutivo <= 0) {
            throw new ClaveEmpleadoFormatoInvalidoException(clave);
        }
        return new EmpleadoId(PREFIX, consecutivo);
    }
}
