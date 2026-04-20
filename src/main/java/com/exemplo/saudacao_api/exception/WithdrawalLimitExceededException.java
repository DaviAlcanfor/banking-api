package com.exemplo.saudacao_api.exception;

public class WithdrawalLimitExceededException extends RuntimeException {
    public WithdrawalLimitExceededException(double limit) {
        super("Limite de saque excedido — máximo permitido: R$ " + limit);
    }
}