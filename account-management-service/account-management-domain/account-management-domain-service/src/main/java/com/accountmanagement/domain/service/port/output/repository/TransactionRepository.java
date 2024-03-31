package com.accountmanagement.domain.service.port.output.repository;

import com.accountmanagement.domain.core.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findTransactionsByAccountId(UUID accountId, int limit);
}
