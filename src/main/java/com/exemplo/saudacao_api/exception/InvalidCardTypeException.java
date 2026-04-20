package com.exemplo.saudacao_api.exception;

public class InvalidCardTypeException extends RuntimeException {
    public InvalidCardTypeException() {
        super("Operação disponível apenas para cartão de crédito");
    }
}

