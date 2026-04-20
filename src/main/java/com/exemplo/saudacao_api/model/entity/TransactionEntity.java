package com.exemplo.saudacao_api.model.entity;

import com.exemplo.saudacao_api.model.types.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "target_client_id")
    private ClientEntity targetClient;


    public TransactionEntity() {}

    public TransactionEntity(
            TransactionType type,
            double amount,
            ClientEntity client,
            ClientEntity targetClient
    ) {
        this.type = type;
        this.amount = amount;
        this.client = client;
        this.targetClient = targetClient;
        this.timestamp = LocalDateTime.now();
    }
}