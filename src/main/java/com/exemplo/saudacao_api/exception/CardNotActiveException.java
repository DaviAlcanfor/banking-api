package com.exemplo.saudacao_api.exception;

public class CardNotActiveException extends RuntimeException {
    public CardNotActiveException() {
        super("Cartão não está ativo");
    }
}