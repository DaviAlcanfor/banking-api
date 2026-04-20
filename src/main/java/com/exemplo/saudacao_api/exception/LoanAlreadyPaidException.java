package com.exemplo.saudacao_api.exception;

public class LoanAlreadyPaidException extends RuntimeException {
    public LoanAlreadyPaidException() {
        super("Empréstimo já quitado");
    }
}
