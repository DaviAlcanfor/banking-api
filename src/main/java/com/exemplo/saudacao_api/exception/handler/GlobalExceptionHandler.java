package com.exemplo.saudacao_api.exception.handler;

import com.exemplo.saudacao_api.exception.*;
import com.exemplo.saudacao_api.response.ApiError;
import com.exemplo.saudacao_api.response.ApiErrorType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyExists(
            UsernameAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.USERNAME_ALREADY_EXISTS.status)
                .body(ApiError.of(ApiErrorType.USERNAME_ALREADY_EXISTS, ex.getMessage()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiError> handleClientNotFound(
            ClientNotFoundException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.CLIENT_NOT_FOUND.status)
                .body(ApiError.of(ApiErrorType.CLIENT_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiError> handleInsufficientBalance(
            InsufficientBalanceException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.INSUFFICIENT_BALANCE.status)
                .body(ApiError.of(ApiErrorType.INSUFFICIENT_BALANCE, ex.getMessage()));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiError> handleInvalidAmount(
            InvalidAmountException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.INVALID_AMOUNT.status)
                .body(ApiError.of(ApiErrorType.INVALID_AMOUNT, ex.getMessage()));
    }

    @ExceptionHandler(SameAccountTransferException.class)
    public ResponseEntity<ApiError> handleSameAccountTransfer(
            SameAccountTransferException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.SAME_ACCOUNT_TRANSFER.status)
                .body(ApiError.of(ApiErrorType.SAME_ACCOUNT_TRANSFER, ex.getMessage()));
    }

    @ExceptionHandler(WithdrawalLimitExceededException.class)
    public ResponseEntity<ApiError> handleWithdrawalLimit(
            WithdrawalLimitExceededException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.WITHDRAWAL_LIMIT_EXCEEDED.status)
                .body(ApiError.of(ApiErrorType.WITHDRAWAL_LIMIT_EXCEEDED, ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex
    ) {
        return ResponseEntity
                .status(ApiErrorType.DATA_INTEGRITY_VIOLATION.status)
                .body(ApiError.of(ApiErrorType.DATA_INTEGRITY_VIOLATION));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleGeneric(RuntimeException ex) {
        return ResponseEntity
                .status(ApiErrorType.INTERNAL_ERROR.status)
                .body(ApiError.of(ApiErrorType.INTERNAL_ERROR, ex.getMessage()));
    }
}