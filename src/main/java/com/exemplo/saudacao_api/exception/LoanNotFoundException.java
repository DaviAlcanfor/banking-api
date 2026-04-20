package com.exemplo.saudacao_api.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Empréstimo não encontrado: " + id);
    }
}
