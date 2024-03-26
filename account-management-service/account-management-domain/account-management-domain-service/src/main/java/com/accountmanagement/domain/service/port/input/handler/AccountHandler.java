package com.accountmanagement.domain.service.port.input.handler;

import com.accountmanagement.domain.core.AccountManagementService;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.event.AccountMovementEvent;
import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.core.valueobject.AccountMovementType;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.service.create.*;
import com.accountmanagement.domain.service.mapper.AccountDataMapper;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AccountHandler {

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final AccountDataMapper accountDataMapper;

    private final AccountManagementService accountManagementService;

    @Transactional
    public CreateAccountResponse createAccount(CreateAccountCommand createAccountCommand) {
        Optional<Customer> customer = customerRepository.findByCustomerId(createAccountCommand.customerId());
        if (customer.isEmpty()) {
            log.warn("Could not find Customer with customerId: {}", createAccountCommand.customerId().toString());
            throw new AccountDomainException("Could not find Customer with customerId: " + createAccountCommand.customerId());
        }

        return accountDataMapper.createAccountResponseFromAccount(
                accountRepository.save(accountDataMapper.createAccountCommandToAccount(createAccountCommand, customer.get()))
        );

    }
    @Transactional
    public CreateAccountBalanceResponse getAccountBalance(CreateAccountBalanceCommand createAccountBalanceCommand) {
        Optional<Account> account = accountRepository.findByAccountId(createAccountBalanceCommand.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", createAccountBalanceCommand.accountId().toString());
            throw new AccountDomainException("Could not find Account with accountId: " + createAccountBalanceCommand.accountId());
        }
        return accountDataMapper.createAccountBalanceResponseFromAccount(account.get());
    }

    public TransactionQueryResponse getTransactions(TransactionQuery transactionQuery) {
        Optional<Account> account = accountRepository.findByAccountId(transactionQuery.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", transactionQuery.accountId().toString());
            throw new AccountDomainException("Could not find Account with accountId: " + transactionQuery.accountId());
        }

        List<Transaction> transactionList = accountRepository.findTransactionsByAccountId(account.get().getAccountId().getValue());
        return accountDataMapper.createTransactionQueryResponse(transactionList, transactionQuery.limit());
    }

    public CreateAccountBalanceResponse doAccountMovement(CreateAccountMovementCommand createAccountMovementCommand) {
        Optional<Account> account = accountRepository.findByAccountId(createAccountMovementCommand.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", createAccountMovementCommand.accountId().toString());
            throw new AccountDomainException("Could not find Account with accountId: " + createAccountMovementCommand.accountId());
        }
        AccountMovementType accountMovementType  = AccountMovementType.isValidAccountMovementType(createAccountMovementCommand.movementType());

        return switch (accountMovementType) {
            case DEPOSIT -> {
                AccountMovementEvent movementEvent = accountManagementService.deposit(account.get(), Money.createWithScale(createAccountMovementCommand.amount()));
                transactionRepository.save(accountDataMapper.createTransaction(createAccountMovementCommand, movementEvent));
                yield accountDataMapper.createCreateAccountBalanceResponse(movementEvent);
            }
            case WITHDRAW -> {
                AccountMovementEvent movementEvent = accountManagementService.withdraw(account.get(), Money.createWithScale(createAccountMovementCommand.amount()));
                transactionRepository.save(accountDataMapper.createTransaction(createAccountMovementCommand, movementEvent));
                yield accountDataMapper.createCreateAccountBalanceResponse(movementEvent);
            }
        };
    }
}
