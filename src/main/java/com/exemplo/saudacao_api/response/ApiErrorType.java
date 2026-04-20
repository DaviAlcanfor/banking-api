package com.exemplo.saudacao_api.response;

import org.springframework.http.HttpStatus;

public enum ApiErrorType {
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Conflict", "Username já existe"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "Cliente não encontrado"),
    INSUFFICIENT_BALANCE(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Saldo insuficiente para realizar a operação"),
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "Bad Request", "Valor inválido — deve ser maior que zero"),
    SAME_ACCOUNT_TRANSFER(HttpStatus.BAD_REQUEST, "Bad Request", "Não é possível transferir para a mesma conta"),
    WITHDRAWAL_LIMIT_EXCEEDED(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Limite de saque excedido"),
    DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "Conflict", "Violação de integridade no banco de dados"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Erro interno"),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "Cartão não encontrado"),
    CARD_NOT_ACTIVE(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Cartão não está ativo"),
    CARD_CANCELLED(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Cartão cancelado não pode ser bloqueado"),
    INSUFFICIENT_LIMIT(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Limite insuficiente"),
    INVALID_CARD_TYPE(HttpStatus.BAD_REQUEST, "Bad Request", "Operação disponível apenas para cartão de crédito"),
    INVALID_BILL_PAYMENT(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Valor maior que a fatura atual"),
    LOAN_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "Empréstimo não encontrado"),
    LOAN_ALREADY_PAID(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", "Empréstimo já quitado");

    public final HttpStatus status;
    public final String error;
    public final String defaultMessage;

    ApiErrorType(HttpStatus status, String error, String defaultMessage) {
        this.status = status;
        this.error = error;
        this.defaultMessage = defaultMessage;
    }
}