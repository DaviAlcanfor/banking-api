package com.exemplo.saudacao_api.service;

import com.exemplo.saudacao_api.exception.ClientNotFoundException;
import com.exemplo.saudacao_api.exception.UsernameAlreadyExistsException;
import com.exemplo.saudacao_api.model.dto.ClientDTO;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.types.AccountType;
import com.exemplo.saudacao_api.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(
            ClientRepository clientRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDTO createClient(
            String username,
            String password,
            double balance,
            AccountType accountType
    ) {
        if (clientRepository.existsByUsername(username))
            throw new UsernameAlreadyExistsException(username);

        var encoded = passwordEncoder.encode(password);
        var client = new ClientEntity(username, encoded, balance, accountType);
        var saved = clientRepository.save(client);

        return toDTO(saved);
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
                .map(client -> passwordEncoder.matches(password, client.getPassword()))
                .orElse(false);
    }

    public ClientDTO getBalance(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        return toDTO(client);
    }

    private ClientDTO toDTO(ClientEntity client) {
        return new ClientDTO(client.getUsername(), client.getBalance(), client.getAccountType());
    }
}