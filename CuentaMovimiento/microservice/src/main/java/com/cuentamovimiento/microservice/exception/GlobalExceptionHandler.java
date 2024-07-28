package com.cuentamovimiento.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoException.class)
    public ResponseEntity<ErrorResponse> handleSaldoException(SaldoException ex) {
        ErrorResponse error = new ErrorResponse("Saldo no disponible", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
