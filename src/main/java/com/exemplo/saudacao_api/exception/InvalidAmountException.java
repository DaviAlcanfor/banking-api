package com.exemplo.saudacao_api.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("Valor inválido — deve ser maior que zero");
    }
}