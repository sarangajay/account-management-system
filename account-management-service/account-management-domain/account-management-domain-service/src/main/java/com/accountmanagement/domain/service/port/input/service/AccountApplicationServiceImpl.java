package com.accountmanagement.domain.service.port.input.service;

import com.accountmanagement.domain.service.create.*;
import com.accountmanagement.domain.service.port.input.handler.AccountHandler;
import com.accountmanagement.domain.service.port.input.handler.CustomerHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
class AccountApplicationServiceImpl implements AccountApplicationService {

    private final CustomerHandler customerHandler;

    private final AccountHandler accountHandler;

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        return customerHandler.createCustomer(createCustomerCommand);
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountCommand createAccountCommand) {
        return accountHandler.createAccount(createAccountCommand);
    }

    @Override
    public CreateAccountBalanceResponse getAccountBalance(CreateAccountBalanceCommand createAccountBalanceCommand) {
        return accountHandler.getAccountBalance(createAccountBalanceCommand);
    }

    @Override
    public TransactionQueryResponse queryTransaction(TransactionQuery transactionQuery) {
        return accountHandler.getTransactions(transactionQuery);
    }

    @Override
    public CreateAccountBalanceResponse doAccountMovement(CreateAccountMovementCommand createAccountMovementCommand) {
        return accountHandler.doAccountMovement(createAccountMovementCommand);
    }
}
