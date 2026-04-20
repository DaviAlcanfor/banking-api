package com.exemplo.saudacao_api.repository;

import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.entity.TransactionEntity;
import com.exemplo.saudacao_api.model.types.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByClient(ClientEntity client);

    List<TransactionEntity> findByClientOrTargetClient(ClientEntity client, ClientEntity targetClient);

    List<TransactionEntity> findByType(TransactionType type);
}