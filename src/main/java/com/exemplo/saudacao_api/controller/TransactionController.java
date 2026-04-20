package com.exemplo.saudacao_api.controller;

import com.exemplo.saudacao_api.model.dto.TransactionDTO;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.service.ClientService;
import com.exemplo.saudacao_api.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    private final ClientService clientService;
    private final TransactionService transactionService;

    public TransactionController(
            ClientService clientService,
            TransactionService transactionService
    ) {
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @PostMapping("/deposito")
    public String depositar(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Double value
    ) {
        if (!clientService.validateUser(username, password))
            return "Acesso negado!";

        return transactionService.deposit(username, value);
    }

    @PostMapping("/saque")
    public String sacar(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Double value,
            @RequestParam(required = false) AccountType accountType
    ) {
        if (!clientService.validateUser(username, password))
            return "Acesso negado!";

        return transactionService.withdraw(username, value, accountType);
    }

    @PostMapping("/transferencia")
    public String transferir(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String toUsername,
            @RequestParam Double value
    ) {
        if (!clientService.validateUser(username, password))
            return "Acesso negado!";

        return transactionService.transfer(username, toUsername, value);
    }

    @GetMapping("/historico")
    public List<TransactionDTO> historico(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return List.of();

        return transactionService.getHistory(username);
    }
}