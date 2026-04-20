package com.exemplo.saudacao_api.exception;

public class InsufficientLimitException extends RuntimeException {
    public InsufficientLimitException() {
        super("Limite insuficiente para realizar a operação");
    }
}

