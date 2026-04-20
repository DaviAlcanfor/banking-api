package com.exemplo.saudacao_api.service;

import com.exemplo.saudacao_api.exception.ClientNotFoundException;
import com.exemplo.saudacao_api.exception.InvalidAmountException;
import com.exemplo.saudacao_api.exception.LoanAlreadyPaidException;
import com.exemplo.saudacao_api.exception.LoanNotFoundException;
import com.exemplo.saudacao_api.model.dto.LoanDTO;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.entity.LoanEntity;
import com.exemplo.saudacao_api.model.types.LoanStatus;
import com.exemplo.saudacao_api.repository.ClientRepository;
import com.exemplo.saudacao_api.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;

    public LoanService(
            LoanRepository loanRepository,
            ClientRepository clientRepository
    ) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
    }

    public LoanDTO requestLoan(
            String username,
            double amount,
            int installments,
            double interestRate,
            boolean compoundInterest
    ) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        double installmentValue;
        if (compoundInterest) {
            double r = interestRate / 100;
            installmentValue = amount * (r * Math.pow(1 + r, installments)) / (Math.pow(1 + r, installments) - 1);
        } else {
            installmentValue = (amount + amount * (interestRate / 100) * installments) / installments;
        }

        double remainingAmount = installmentValue * installments;
        var requestDate = LocalDate.now();
        var dueDate = requestDate.plusMonths(installments);

        var loan = new LoanEntity(amount, installments, installmentValue, interestRate, compoundInterest, client);
        loan.setRemainingAmount(remainingAmount);
        loan.setRequestDate(requestDate);
        loan.setDueDate(dueDate);
        loan.setStatus(LoanStatus.ATIVO);

        var saved = loanRepository.save(loan);
        return toDTO(saved);
    }

    public List<LoanDTO> getLoans(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        return loanRepository.findByClient(client)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public LoanDTO payInstallment(Long loanId, double value) {
        var loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        if (value <= 0)
            throw new InvalidAmountException();

        if (loan.getStatus() == LoanStatus.QUITADO)
            throw new LoanAlreadyPaidException();

        loan.setRemainingAmount(loan.getRemainingAmount() - value);

        if (loan.getRemainingAmount() <= 0) {
            loan.setStatus(LoanStatus.QUITADO);
            loan.setRemainingAmount(0);
        }

        var saved = loanRepository.save(loan);
        return toDTO(saved);
    }

    private LoanDTO toDTO(LoanEntity loan) {
        return new LoanDTO(
                loan.getAmount(),
                loan.getRemainingAmount(),
                loan.getInstallments(),
                loan.getInstallmentValue(),
                loan.getInterestRate(),
                loan.isCompoundInterest(),
                loan.getStatus(),
                loan.getRequestDate(),
                loan.getDueDate(),
                loan.getClient().getUsername()
        );
    }
}
