package com.accountmanagement.application.rest;

import com.accountmanagement.domain.service.create.*;
import com.accountmanagement.domain.service.port.input.service.AccountApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/accounts", produces = "application/vnd.api.v1+json")
public class AccountController {

    private final AccountApplicationService accountApplicationService;

    @PostMapping("/customer")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerCommand createCustomerCommand) {
        log.info("Create Customer request with {}", createCustomerCommand.toString());
        CreateCustomerResponse createCustomerResponse = accountApplicationService.createCustomer(createCustomerCommand);
        log.info("Customer created {}", createCustomerResponse.toString());
        return ResponseEntity.ok(createCustomerResponse);
    }

    @PostMapping("/account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountCommand createAccountCommand) {
        log.info("Create Account request with {}", createAccountCommand.toString());
        CreateAccountResponse createAccountResponse = accountApplicationService.createAccount(createAccountCommand);
        log.info("Account created {}", createAccountResponse.toString());
        return ResponseEntity.ok(createAccountResponse);
    }

    @GetMapping("/account/balance")
    public  ResponseEntity<CreateAccountBalanceResponse> getAccountBalance(@RequestBody CreateAccountBalanceCommand createAccountBalanceCommand) {
        log.info("Get Account balance request with {}", createAccountBalanceCommand.toString());
        CreateAccountBalanceResponse createAccountBalanceResponse = accountApplicationService.getAccountBalance(createAccountBalanceCommand);
        log.info("Account balance response :  {}", createAccountBalanceResponse.toString());
        return ResponseEntity.ok(createAccountBalanceResponse);
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<CreateAccountBalanceResponse> doDeposit(@RequestBody CreateAccountMovementCommand createAccountMovementCommand) {
        log.info("Account deposit request with {}", createAccountMovementCommand.toString());
        CreateAccountBalanceResponse createAccountBalanceResponse =  accountApplicationService.doAccountMovement(createAccountMovementCommand);
        log.info("Account balance response after deposit :  {}", createAccountBalanceResponse.toString());
        return ResponseEntity.ok(createAccountBalanceResponse);
    }

    @PostMapping("/account/withdraw")
    public ResponseEntity<CreateAccountBalanceResponse> doWithdraw(@RequestBody CreateAccountMovementCommand createAccountMovementCommand) {
        log.info("Account withdraw request with {}", createAccountMovementCommand.toString());
        CreateAccountBalanceResponse createAccountBalanceResponse =  accountApplicationService.doAccountMovement(createAccountMovementCommand);
        log.info("Account balance response after withdraw :  {}", createAccountBalanceResponse.toString());
        return ResponseEntity.ok(createAccountBalanceResponse);
    }

    @GetMapping("/account/transaction")
    public ResponseEntity<TransactionQueryResponse> queryTransactions(@RequestBody TransactionQuery transactionQuery) {
        log.info("Query transactions request with {}", transactionQuery.toString());
        TransactionQueryResponse transactionQueryResponse = accountApplicationService.queryTransaction(transactionQuery);
        log.info("Query transactions response with {}", transactionQueryResponse.transactions().size());
        return ResponseEntity.ok(transactionQueryResponse);
    }

}
