package com.exemplo.saudacao_api.model.dto;

import com.exemplo.saudacao_api.model.types.CardStatus;
import com.exemplo.saudacao_api.model.types.CardTier;
import com.exemplo.saudacao_api.model.types.CardType;
import com.exemplo.saudacao_api.model.types.CardVariant;

import java.time.LocalDate;

public record CardDTO(
        String number,
        CardType type,
        CardTier tier,
        CardVariant variant,
        CardStatus status,
        double usedLimit,
        double availableLimit,
        LocalDate expirationDate,
        String username
) {}