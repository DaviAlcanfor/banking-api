package com.exemplo.saudacao_api.repository;

import com.exemplo.saudacao_api.model.entity.CardEntity;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.types.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findByClient(ClientEntity client);

    List<CardEntity> findByClientAndStatus(ClientEntity client, CardStatus status);

    boolean existsByNumber(String number);
}