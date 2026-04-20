package com.exemplo.saudacao_api.controller;

import com.exemplo.saudacao_api.model.dto.ClientDTO;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banco")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/cliente")
    public ClientDTO criarCliente(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam double balance,
            @RequestParam(required = false) AccountType accountType
    ) {
        return clientService.createClient(username, password, balance, accountType);
    }

    @GetMapping("/saldo")
    public String getSaldo(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return "Acesso negado!";

        return clientService.getBalance(username);
    }
}