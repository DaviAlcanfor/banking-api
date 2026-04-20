package com.exemplo.saudacao_api.model.entity;

import com.exemplo.saudacao_api.model.types.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private double remainingAmount;
    private int installments;
    private double installmentValue;
    private double interestRate;
    private boolean compoundInterest;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate requestDate;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    public LoanEntity() {}

    public LoanEntity(
            double amount,
            int installments,
            double installmentValue,
            double interestRate,
            boolean compoundInterest,
            ClientEntity client
    ) {
        this.amount = amount;
        this.installments = installments;
        this.installmentValue = installmentValue;
        this.interestRate = interestRate;
        this.compoundInterest = compoundInterest;
        this.client = client;
    }
}
