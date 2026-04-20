package com.exemplo.saudacao_api.service;

import com.exemplo.saudacao_api.exception.*;
import com.exemplo.saudacao_api.model.dto.TransactionDTO;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.entity.TransactionEntity;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.model.types.TransactionType;
import com.exemplo.saudacao_api.repository.ClientRepository;
import com.exemplo.saudacao_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            ClientRepository clientRepository,
            TransactionRepository transactionRepository
    ) {
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO deposit(String username, Double value) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        if (value == null || value <= 0)
            throw new InvalidAmountException();

        client.setBalance(client.getBalance() + value);
        clientRepository.save(client);

        return toDTO(saveTransaction(TransactionType.DEPOSITO, value, client, null));
    }

    /**
     * Realiza saque aplicando as regras de cada tipo de conta.
     * Poupança: limite de R$ 500 por saque.
     * Corrente: permite saldo negativo até -R$ 200 (limite).
     * Sem tipo definido: saque simples sem cheque especial.
     *
     * @param username username do cliente
     * @param value valor a ser sacado
     * @param accountType tipo da conta (pode ser null)
     * @return TransactionDTO com resultado da operação
     */
    public TransactionDTO withdraw(
            String username,
            Double value,
            AccountType accountType
    ) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        if (value == null || value <= 0)
            throw new InvalidAmountException();

        final double POUPANCA_MAX = 500;
        final double CORRENTE_LIMIT = -200;

        if (accountType == null) {
            if (value > client.getBalance())
                throw new InsufficientBalanceException();

            client.setBalance(client.getBalance() - value);
            clientRepository.save(client);

            return toDTO(saveTransaction(TransactionType.SAQUE, value, client, null));
        }

        return switch (accountType) {
            case POUPANCA -> {
                if (value > POUPANCA_MAX)
                    throw new WithdrawalLimitExceededException(POUPANCA_MAX);

                client.setBalance(client.getBalance() - value);
                clientRepository.save(client);

                yield toDTO(saveTransaction(TransactionType.SAQUE, value, client, null));
            }
            case CORRENTE -> {
                double newBalance = client.getBalance() - value;
                if (newBalance < CORRENTE_LIMIT)
                    throw new InsufficientBalanceException();

                client.setBalance(newBalance);
                clientRepository.save(client);

                yield toDTO(saveTransaction(TransactionType.SAQUE, value, client, null));
            }
        };
    }

    /**
     * Realiza transferência entre dois clientes.
     * Debita o valor do cliente origem e credita no destino.
     * Registra a transação no histórico de ambos.
     *
     * @param fromUsername username do cliente que está enviando
     * @param toUsername username do cliente que está recebendo
     * @param value valor a ser transferido (deve ser positivo)
     * @return TransactionDTO com resultado da operação
     */
    public TransactionDTO transfer(
            String fromUsername,
            String toUsername,
            Double value
    ) {
        var from = clientRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new ClientNotFoundException(fromUsername));

        var to = clientRepository.findByUsername(toUsername)
                .orElseThrow(() -> new ClientNotFoundException(toUsername));

        if (value == null || value <= 0)
            throw new InvalidAmountException();

        if (fromUsername.equals(toUsername))
            throw new SameAccountTransferException();

        if (from.getBalance() < value)
            throw new InsufficientBalanceException();

        from.setBalance(from.getBalance() - value);
        to.setBalance(to.getBalance() + value);

        clientRepository.save(from);
        clientRepository.save(to);

        return toDTO(saveTransaction(TransactionType.TRANSFERENCIA, value, from, to));
    }

    public List<TransactionDTO> getHistory(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        return transactionRepository.findByClientOrTargetClient(client, client)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private TransactionEntity saveTransaction(
            TransactionType type,
            Double value,
            ClientEntity client,
            ClientEntity targetClient
    ) {
        var transaction = new TransactionEntity(type, value, client, targetClient);
        return transactionRepository.save(transaction);
    }

    private TransactionDTO toDTO(TransactionEntity t) {
        return new TransactionDTO(
                t.getType(),
                t.getAmount(),
                t.getClient().getUsername(),
                t.getTargetClient() != null ? t.getTargetClient().getUsername() : null,
                t.getTimestamp()
        );
    }
}