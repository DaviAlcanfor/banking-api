package com.exemplo.saudacao_api.model.entity;

import com.exemplo.saudacao_api.model.types.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private double balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public ClientEntity() {}

    public ClientEntity(String username, String password, double balance, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.accountType = accountType;
    }

    public ClientEntity(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
}