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

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ApiError> handleCardNotFound(CardNotFoundException ex) {
        return ResponseEntity
                .status(ApiErrorType.CARD_NOT_FOUND.status)
                .body(ApiError.of(ApiErrorType.CARD_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(CardNotActiveException.class)
    public ResponseEntity<ApiError> handleCardNotActive(CardNotActiveException ex) {
        return ResponseEntity
                .status(ApiErrorType.CARD_NOT_ACTIVE.status)
                .body(ApiError.of(ApiErrorType.CARD_NOT_ACTIVE, ex.getMessage()));
    }

    @ExceptionHandler(CardCancelledException.class)
    public ResponseEntity<ApiError> handleCardCancelled(CardCancelledException ex) {
        return ResponseEntity
                .status(ApiErrorType.CARD_CANCELLED.status)
                .body(ApiError.of(ApiErrorType.CARD_CANCELLED, ex.getMessage()));
    }

    @ExceptionHandler(InsufficientLimitException.class)
    public ResponseEntity<ApiError> handleInsufficientLimit(InsufficientLimitException ex) {
        return ResponseEntity
                .status(ApiErrorType.INSUFFICIENT_LIMIT.status)
                .body(ApiError.of(ApiErrorType.INSUFFICIENT_LIMIT, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCardTypeException.class)
    public ResponseEntity<ApiError> handleInvalidCardType(InvalidCardTypeException ex) {
        return ResponseEntity
                .status(ApiErrorType.INVALID_CARD_TYPE.status)
                .body(ApiError.of(ApiErrorType.INVALID_CARD_TYPE, ex.getMessage()));
    }

    @ExceptionHandler(InvalidBillPaymentException.class)
    public ResponseEntity<ApiError> handleInvalidBillPayment(InvalidBillPaymentException ex) {
        return ResponseEntity
                .status(ApiErrorType.INVALID_BILL_PAYMENT.status)
                .body(ApiError.of(ApiErrorType.INVALID_BILL_PAYMENT, ex.getMessage()));
    }
}