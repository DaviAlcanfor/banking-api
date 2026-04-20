package com.exemplo.saudacao_api.exception;

public class SameAccountTransferException extends RuntimeException {
    public SameAccountTransferException() {
        super("Não é possível transferir para a mesma conta");
    }
}