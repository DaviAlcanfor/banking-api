package com.exemplo.saudacao_api.model.dto;

import com.exemplo.saudacao_api.model.types.LoanStatus;

import java.time.LocalDate;

public record LoanDTO(
        double amount,
        double remainingAmount,
        int installments,
        double installmentValue,
        double interestRate,
        boolean compoundInterest,
        LoanStatus status,
        LocalDate requestDate,
        LocalDate dueDate,
        String username
) {}
