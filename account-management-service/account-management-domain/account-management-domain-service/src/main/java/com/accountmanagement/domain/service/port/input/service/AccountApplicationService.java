package com.accountmanagement.domain.service.port.input.service;

import com.accountmanagement.domain.service.create.*;
import jakarta.validation.Valid;

public interface AccountApplicationService {
    CreateCustomerResponse createCustomer(@Valid CreateCustomerCommand createCustomerCommand);
    CreateAccountResponse createAccount(@Valid CreateAccountCommand createAccountCommand);
    CreateAccountBalanceResponse getAccountBalance(@Valid CreateAccountBalanceCommand createAccountBalanceCommand);
    TransactionQueryResponse queryTransaction(@Valid TransactionQuery transactionQuery);
    CreateAccountBalanceResponse doAccountMovement(@Valid CreateAccountMovementCommand createAccountMovementCommand);
}
