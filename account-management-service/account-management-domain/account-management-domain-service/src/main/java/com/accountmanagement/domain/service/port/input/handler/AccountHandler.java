package com.accountmanagement.domain.service.port.input.handler;

import com.accountmanagement.domain.core.AccountManagementService;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.event.AccountMovementEvent;
import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.core.exception.AccountNotFoundException;
import com.accountmanagement.domain.core.exception.CustomerNotFoundException;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.valueobject.TransactionId;
import com.accountmanagement.domain.core.valueobject.TransactionType;
import com.accountmanagement.domain.service.create.*;
import com.accountmanagement.domain.service.mapper.AccountDataMapper;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            throw new CustomerNotFoundException("Could not find Customer with customerId: " + createAccountCommand.customerId());
        }
        Account account = accountRepository.save(accountDataMapper.createAccountCommandToAccount(createAccountCommand, customer.get()));
        Transaction transaction = Transaction
                .builder()
                .transactionId(new TransactionId(UUID.randomUUID()))
                .money(account.getBalance())
                .account(account)
                .transactionType(TransactionType.DEBIT)
                .transactionTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
        return accountDataMapper.createAccountResponseFromAccount(account);
    }

    public CreateAccountBalanceResponse getAccountBalance(CreateAccountBalanceCommand createAccountBalanceCommand) {
        Optional<Account> account = accountRepository.findByAccountId(createAccountBalanceCommand.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", createAccountBalanceCommand.accountId().toString());
            throw new AccountNotFoundException("Could not find Account with accountId: " + createAccountBalanceCommand.accountId());
        }
        return accountDataMapper.createAccountBalanceResponseFromAccount(account.get());
    }

    public TransactionQueryResponse getTransactions(TransactionQuery transactionQuery) {
        Optional<Account> account = accountRepository.findByAccountId(transactionQuery.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", transactionQuery.accountId().toString());
            throw new AccountDomainException("Could not find Account with accountId: " + transactionQuery.accountId());
        }

        List<Transaction> transactionList = transactionRepository.findTransactionsByAccountId(account.get().getAccountId().getValue(),
                transactionQuery.limit());
        return accountDataMapper.createTransactionQueryResponse(transactionList, transactionQuery.limit());
    }

    @Transactional
    public synchronized CreateAccountBalanceResponse doAccountMovement(CreateAccountMovementCommand createAccountMovementCommand) {
        Optional<Account> account = accountRepository.findByAccountId(createAccountMovementCommand.accountId());
        if (account.isEmpty()) {
            log.warn("Could not find Account with accountId: {}", createAccountMovementCommand.accountId().toString());
            throw new AccountNotFoundException("Could not find Account with accountId: " + createAccountMovementCommand.accountId());
        }
        TransactionType accountMovementType = TransactionType.isValidTransactionType(createAccountMovementCommand.movementType());

        return switch (accountMovementType) {
            case DEBIT -> {
                AccountMovementEvent movementEvent = accountManagementService.deposit(account.get(), Money.createWithScale(createAccountMovementCommand.amount()));
                accountRepository.save(movementEvent.account());
                transactionRepository.save(accountDataMapper.createTransaction(createAccountMovementCommand, movementEvent));
                yield accountDataMapper.createCreateAccountBalanceResponse(movementEvent);
            }
            case CREDIT -> {
                AccountMovementEvent movementEvent = accountManagementService.withdraw(account.get(), Money.createWithScale(createAccountMovementCommand.amount()));
                accountRepository.save(movementEvent.account());
                transactionRepository.save(accountDataMapper.createTransaction(createAccountMovementCommand, movementEvent));
                yield accountDataMapper.createCreateAccountBalanceResponse(movementEvent);
            }
        };
    }
}
