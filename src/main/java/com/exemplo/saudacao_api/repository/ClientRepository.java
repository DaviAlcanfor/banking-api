package com.exemplo.saudacao_api.repository;

import com.exemplo.saudacao_api.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}