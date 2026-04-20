package com.exemplo.saudacao_api.exception;

public class CardCancelledException extends RuntimeException {
    public CardCancelledException() {
        super("Cartão cancelado não pode ser bloqueado");
    }
}

