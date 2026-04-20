package com.exemplo.saudacao_api.controller;

import com.exemplo.saudacao_api.model.dto.LoanDTO;
import com.exemplo.saudacao_api.service.ClientService;
import com.exemplo.saudacao_api.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class LoanController {

    private final LoanService loanService;
    private final ClientService clientService;

    public LoanController(
            LoanService loanService,
            ClientService clientService
    ) {
        this.loanService = loanService;
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> requestLoan(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam double amount,
            @RequestParam int installments,
            @RequestParam double interestRate,
            @RequestParam boolean compoundInterest
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.requestLoan(username, amount, installments, interestRate, compoundInterest));
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listLoans(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(loanService.getLoans(username));
    }

    @PostMapping("/{loanId}/pagar")
    public ResponseEntity<LoanDTO> payInstallment(
            @PathVariable Long loanId,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam double value
    ) {
        if (!clientService.validateUser(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(loanService.payInstallment(loanId, value));
    }
}
