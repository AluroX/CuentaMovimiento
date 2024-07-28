package com.cuentamovimiento.microservice.exception;

public class SaldoException extends RuntimeException {
    public SaldoException(String message) {
        super(message);
    }
}