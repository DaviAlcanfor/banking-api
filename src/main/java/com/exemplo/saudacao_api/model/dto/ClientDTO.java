package com.exemplo.saudacao_api.model.dto;

import com.exemplo.saudacao_api.model.types.AccountType;

public record ClientDTO(
        String username,
        double balance,
        AccountType accountType
) {}