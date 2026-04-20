package com.exemplo.saudacao_api.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long id) {
        super("Cartão não encontrado: " + id);
    }
}