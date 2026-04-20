package com.exemplo.saudacao_api.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username já existe: " + username);
    }
}