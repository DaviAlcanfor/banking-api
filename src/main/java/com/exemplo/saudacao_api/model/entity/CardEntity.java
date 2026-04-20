package com.exemplo.saudacao_api.model.entity;

import com.exemplo.saudacao_api.model.types.CardStatus;
import com.exemplo.saudacao_api.model.types.CardTier;
import com.exemplo.saudacao_api.model.types.CardType;
import com.exemplo.saudacao_api.model.types.CardVariant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String cvv;
    private LocalDate expirationDate;
    private double usedLimit;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Enumerated(EnumType.STRING)
    private CardVariant variant;

    @Enumerated(EnumType.STRING)
    private CardTier tier;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    public CardEntity() {}

    public CardEntity(String number, String cvv, LocalDate expirationDate, CardType type) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.type = type;
    }

    public CardEntity(
            String number,
            String cvv,
            LocalDate expirationDate,
            CardType type,
            CardTier tier,
            CardVariant variant,
            ClientEntity client
    ) {
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.type = type;
        this.tier = tier;
        this.variant = variant;
        this.status = CardStatus.ATIVO;
        this.usedLimit = 0;
        this.client = client;
    }
}
