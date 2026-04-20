package com.exemplo.saudacao_api.model.types;

public enum CardTier {
    N1(500),
    N2(1500),
    N3(3000),
    N4(5000);

    public final double limit;

    CardTier(double limit) {
        this.limit = limit;
    }
}