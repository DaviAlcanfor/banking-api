package com.exemplo.saudacao_api.service;

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

    public String deposit(String username, Double value) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (value == null || value <= 0)
            return "Valor inválido para depósito";

        client.setBalance(client.getBalance() + value);
        clientRepository.save(client);

        saveTransaction(TransactionType.DEPOSITO, value, client, null);

        return "Depósito realizado. Novo saldo: R$ " + client.getBalance();
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
     * @return mensagem com resultado da operação
     */
    public String withdraw(String username, Double value, AccountType accountType) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (value == null || value <= 0)
            return "Valor inválido para saque";

        final double POUPANCA_MAX = 500;
        final double CORRENTE_LIMIT = -200;

        if (accountType == null) {
            if (value > client.getBalance())
                return "Saldo insuficiente";

            client.setBalance(client.getBalance() - value);
            clientRepository.save(client);
            saveTransaction(TransactionType.SAQUE, value, client, null);

            return "Saque realizado. Novo saldo: R$ " + client.getBalance();
        }

        return switch (accountType) {
            case POUPANCA -> {
                if (value > POUPANCA_MAX)
                    yield "Saque não permitido para poupança acima de R$ 500";

                client.setBalance(client.getBalance() - value);
                clientRepository.save(client);
                saveTransaction(TransactionType.SAQUE, value, client, null);

                yield "Saque realizado. Novo saldo: R$ " + client.getBalance();
            }
            case CORRENTE -> {
                double newBalance = client.getBalance() - value;
                if (newBalance < CORRENTE_LIMIT)
                    yield "Limite excedido!";

                client.setBalance(newBalance);
                clientRepository.save(client);
                saveTransaction(TransactionType.SAQUE, value, client, null);

                yield newBalance < 0
                        ? "Saque realizado com uso do limite. Saldo: R$ " + newBalance
                        : "Saque realizado. Novo saldo: R$ " + newBalance;
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
     * @return mensagem com resultado e novo saldo do cliente origem
     */
    public String transfer(String fromUsername, String toUsername, Double value) {
        var from = clientRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new RuntimeException("Cliente origem não encontrado"));

        var to = clientRepository.findByUsername(toUsername)
                .orElseThrow(() -> new RuntimeException("Cliente destino não encontrado"));

        if (value == null || value <= 0)
            return "Valor inválido para transferência";

        if (from.getBalance() < value)
            return "Saldo insuficiente";

        from.setBalance(from.getBalance() - value);
        to.setBalance(to.getBalance() + value);

        clientRepository.save(from);
        clientRepository.save(to);
        saveTransaction(TransactionType.TRANSFERENCIA, value, from, to);

        return "Transferência realizada. Novo saldo: R$ " + from.getBalance();
    }

    public List<TransactionDTO> getHistory(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return transactionRepository.findByClientOrTargetClient(client, client)
                .stream()
                .map(t -> new TransactionDTO(
                        t.getType(),
                        t.getAmount(),
                        t.getClient().getUsername(),
                        t.getTargetClient() != null ? t.getTargetClient().getUsername() : null,
                        t.getTimestamp()
                ))
                .toList();
    }

    private void saveTransaction(
            TransactionType type,
            Double value,
            ClientEntity client,
            ClientEntity targetClient
    ) {
        var transaction = new TransactionEntity(type, value, client, targetClient);
        transactionRepository.save(transaction);
    }
}