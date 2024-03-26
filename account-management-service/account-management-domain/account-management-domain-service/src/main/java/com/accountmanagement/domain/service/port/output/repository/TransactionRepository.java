package com.accountmanagement.domain.service.port.output.repository;

import com.accountmanagement.domain.core.entity.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
}
