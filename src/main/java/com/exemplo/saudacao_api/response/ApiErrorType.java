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
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Erro interno");

    public final HttpStatus status;
    public final String error;
    public final String defaultMessage;

    ApiErrorType(HttpStatus status, String error, String defaultMessage) {
        this.status = status;
        this.error = error;
        this.defaultMessage = defaultMessage;
    }
}