package com.exemplo.saudacao_api.repository;

import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.entity.LoanEntity;
import com.exemplo.saudacao_api.model.types.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByClient(ClientEntity client);

    List<LoanEntity> findByClientAndStatus(ClientEntity client, LoanStatus status);
}
