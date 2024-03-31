package com.accountmanagement.dataaccess.transaction.mapper;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import com.accountmanagement.dataaccess.account.mapper.AccountDataAccessMapper;
import com.accountmanagement.dataaccess.transaction.entity.TransactionEntity;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.valueobject.TransactionId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TransactionDataAccessMapper {

    private AccountDataAccessMapper accountDataAccessMapper;
    public TransactionEntity transactionToTransactionEntity(Transaction transaction, AccountEntity accountEntity) {
        return TransactionEntity
                .builder()
                .id(transaction.getTransactionId().getValue())
                .transactionType(transaction.getTransactionType())
                .transactionTime(LocalDateTime.now())
                .amount(transaction.getMoney().amount())
                .account(accountEntity)
                .build();
    }

    public Transaction transactionEntityToTransaction(TransactionEntity transactionEntity) {
        return Transaction
                .builder()
                .transactionId(new TransactionId(transactionEntity.getId()))
                .money(Money.createWithScale(transactionEntity.getAmount()))
                .transactionTime(transactionEntity.getTransactionTime())
                .account(accountDataAccessMapper.accountEntityToAccount(transactionEntity.getAccount()))
                .build();
    }

    public List<Transaction> createTransactionList(List<TransactionEntity> transactionEntities) {
        return transactionEntities
                .stream()
                .map( this::transactionEntityToTransaction)
                .collect(Collectors.toList());
    }
}
