package com.exemplo.saudacao_api.controller;

import com.exemplo.saudacao_api.exception.ClientNotFoundException;
import com.exemplo.saudacao_api.model.dto.ClientDTO;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banco")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/cliente")
    public ResponseEntity<ClientDTO> createClient(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam double balance,
            @RequestParam(required = false) AccountType accountType
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.createClient(username, password, balance, accountType));
    }

    @GetMapping("/saldo")
    public ResponseEntity<ClientDTO> getBalance(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(clientService.getBalance(username));
    }
}