package com.exemplo.saudacao_api.model.dto;

import com.exemplo.saudacao_api.model.types.TransactionType;

import java.time.LocalDateTime;

public record TransactionDTO(
        TransactionType type,
        double amount,
        String username,
        String targetUsername,
        LocalDateTime timestamp
) {}