package com.exemplo.saudacao_api.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String username) {
        super("Cliente não encontrado: " + username);
    }
}