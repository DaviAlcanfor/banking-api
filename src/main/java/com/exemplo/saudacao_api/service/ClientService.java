package com.exemplo.saudacao_api.service;

import com.exemplo.saudacao_api.model.dto.ClientDTO;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO createClient(
            String username,
            String password,
            double balance,
            AccountType accountType
    ) {
        if (clientRepository.existsByUsername(username))
            throw new RuntimeException("Username já existe");

        var client = new ClientEntity(username, password, balance, accountType);
        var saved = clientRepository.save(client);

        return new ClientDTO(saved.getUsername(), saved.getBalance(), saved.getAccountType());
    }

    /**
     * Valida as credenciais de um cliente.
     *
     * @param username nome de usuário
     * @param password senha em texto plano
     * @return true se as credenciais forem válidas, false caso contrário
     */
    public boolean validateUser(String username, String password) {
        return clientRepository.findByUsername(username)
                .map(client -> client.getPassword().equals(password))
                .orElse(false);
    }

    public String getBalance(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return "Seu saldo atual é R$ " + client.getBalance();
    }
}