package com.accountmanagement.domain.service.mapper;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.event.AccountMovementEvent;
import com.accountmanagement.domain.core.valueobject.*;
import com.accountmanagement.domain.service.create.*;
import com.accountmanagement.domain.service.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class AccountDataMapper {

    public Account createAccountCommandToAccount(CreateAccountCommand createAccountCommand, Customer customer) {
        return Account
                .builder()
                .accountId(new AccountId(UUID.randomUUID()))
                .accountType(AccountType.isValidAccountType(createAccountCommand.accountType()))
                .balance(Money.createWithScale(createAccountCommand.amount()))
                .customer(customer)
                .build();
    }

    public CreateAccountResponse createAccountResponseFromAccount(Account account) {
        return CreateAccountResponse
                .builder()
                .accountId(account.getAccountId().getValue())
                .accountType(account.getAccountType().name())
                .balance(account.getBalance().amount())
                .customerId(account.getCustomer().getCustomerId().getValue())
                .build();
    }

    public CreateAccountBalanceResponse createAccountBalanceResponseFromAccount(Account account) {
        return new CreateAccountBalanceResponse(account.getAccountId().getValue(), account.getBalance().amount());
    }

    public TransactionQueryResponse createTransactionQueryResponse(List<Transaction> transactionList, int limit) {
        if (transactionList == null) {
            return new TransactionQueryResponse(Collections.emptyList());
        } else {
            transactionList.sort(new Transaction.TransactionComparator());
            List<TransactionDTO> list = transactionList.stream().limit(limit).toList()
                    .stream()
                    .map(this::transactionToTransactionDTO)
                    .toList();
            return new TransactionQueryResponse(list);
        }
    }

    public CreateAccountBalanceResponse createCreateAccountBalanceResponse(AccountMovementEvent movementEvent) {
        return CreateAccountBalanceResponse
                .builder()
                .accountId(movementEvent.account().getAccountId().getValue())
                .balance(movementEvent.account().getBalance().amount())
                .build();
    }

    private TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        return TransactionDTO
                .builder()
                .transactionId(transaction.getTransactionId().getValue())
                .transactionTime(transaction.getTransactionTime())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getMoney().amount())
                .build();
    }

    public Transaction createTransaction(CreateAccountMovementCommand createAccountMovementCommand, AccountMovementEvent movementEvent) {
        return Transaction
                .builder()
                .transactionId(new TransactionId(UUID.randomUUID()))
                .account(movementEvent.account())
                .money(Money.createWithScale(createAccountMovementCommand.amount()))
                .transactionTime(movementEvent.createdAt().toLocalDateTime())
                .transactionType(TransactionType.valueOf(createAccountMovementCommand.movementType()))
                .build();
    }

}
