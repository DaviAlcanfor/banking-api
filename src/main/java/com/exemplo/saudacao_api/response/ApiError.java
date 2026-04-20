package com.exemplo.saudacao_api.response;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public static ApiError of(ApiErrorType type) {
        return new ApiError(
                type.status.value(),
                type.error,
                type.defaultMessage,
                LocalDateTime.now()
        );
    }

    public static ApiError of(ApiErrorType type, String message) {
        return new ApiError(
                type.status.value(),
                type.error,
                message,
                LocalDateTime.now()
        );
    }
}