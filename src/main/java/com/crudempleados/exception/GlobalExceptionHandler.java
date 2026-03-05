package com.crudempleados.exception;

import com.crudempleados.dto.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", "Solicitud inválida", details));
    }

    @ExceptionHandler(ClaveEmpleadoFormatoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleInvalidKey(ClaveEmpleadoFormatoInvalidoException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_KEY_FORMAT", ex.getMessage(), List.of()));
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EmpleadoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), List.of()));
    }

    @ExceptionHandler(EmpleadoMigracionException.class)
    public ResponseEntity<ErrorResponse> handleMigration(EmpleadoMigracionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse("MIGRATION_ERROR", ex.getMessage(), List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("INTERNAL_ERROR", "Error interno del servidor", List.of()));
    }
}
