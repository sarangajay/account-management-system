package com.accountmanagement.dataaccess.transaction.repository;

import com.accountmanagement.dataaccess.transaction.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJPARepository extends JpaRepository<TransactionEntity, UUID> {

    @Query("SELECT t FROM TransactionEntity t ORDER BY t.transactionTime DESC")
    List<TransactionEntity> findLastNTransactions(Pageable pageable);
}
