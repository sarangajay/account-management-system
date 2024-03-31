package com.accountmanagement.domain.service.port.output.repository;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByAccountId(UUID accountId);
}
