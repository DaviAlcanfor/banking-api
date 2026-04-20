package com.exemplo.saudacao_api.controller;

import com.exemplo.saudacao_api.model.dto.CardDTO;
import com.exemplo.saudacao_api.model.types.CardTier;
import com.exemplo.saudacao_api.model.types.CardType;
import com.exemplo.saudacao_api.model.types.CardVariant;
import com.exemplo.saudacao_api.service.CardService;
import com.exemplo.saudacao_api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    private final CardService cardService;
    private final ClientService clientService;

    public CardController(
            CardService cardService,
            ClientService clientService
    ) {
        this.cardService = cardService;
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam CardType type,
            @RequestParam CardTier tier,
            @RequestParam CardVariant variant
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cardService.createCard(username, type, tier, variant));
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> listCards(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.getCards(username));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CardDTO>> listActiveCards(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.getActiveCards(username));
    }

    @PatchMapping("/{cardId}/bloquear")
    public ResponseEntity<CardDTO> blockCard(
            @PathVariable Long cardId,
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.blockCard(cardId));
    }

    @PatchMapping("/{cardId}/cancelar")
    public ResponseEntity<CardDTO> cancelCard(
            @PathVariable Long cardId,
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.cancelCard(cardId));
    }

    @PostMapping("/{cardId}/usar-limite")
    public ResponseEntity<CardDTO> useLimit(
            @PathVariable Long cardId,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Double value
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.useLimit(cardId, value));
    }

    @PostMapping("/{cardId}/pagar-fatura")
    public ResponseEntity<CardDTO> payBill(
            @PathVariable Long cardId,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Double value
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cardService.payBill(cardId, value));
    }
}