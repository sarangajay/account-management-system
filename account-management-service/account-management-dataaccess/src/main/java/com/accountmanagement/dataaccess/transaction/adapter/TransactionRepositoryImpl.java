package com.accountmanagement.dataaccess.transaction.adapter;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import com.accountmanagement.dataaccess.account.repository.AccountJPARepository;
import com.accountmanagement.dataaccess.transaction.mapper.TransactionDataAccessMapper;
import com.accountmanagement.dataaccess.transaction.repository.TransactionEntityRepository;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;
    private final AccountJPARepository accountJPARepository;
    private final TransactionDataAccessMapper transactionDataAccessMapper;

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction.getAccount() == null) {
            log.warn("Could not find account in Transaction: {}", transaction.getId());
            throw new AccountDomainException("Could not find account in Transaction: " + transaction.getId());
        }
        Optional<AccountEntity> account = accountJPARepository.findById(transaction.getAccount().getAccountId().getValue());
        if (account.isEmpty()) {
            log.warn("Could not find account for: {}", transaction.getAccount().getAccountId().getValue());
            throw new AccountDomainException("Could not find account for: " + transaction.getAccount().getAccountId().getValue());
        }

        return transactionDataAccessMapper.transactionEntityToTransaction(
                transactionEntityRepository.save(transactionDataAccessMapper.transactionToTransactionEntity(transaction, account.get()))
        );
    }

    @Override
    public List<Transaction> findTransactionsByAccountId(UUID accountId, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by("transactionTime").descending());
        return transactionDataAccessMapper.createTransactionList(transactionEntityRepository.findLastNTransactions(pageRequest));
    }
}
