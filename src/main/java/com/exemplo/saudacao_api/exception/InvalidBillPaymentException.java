package com.exemplo.saudacao_api.exception;

public class InvalidBillPaymentException extends RuntimeException {
    public InvalidBillPaymentException() {
        super("Valor maior que a fatura atual");
    }
}

