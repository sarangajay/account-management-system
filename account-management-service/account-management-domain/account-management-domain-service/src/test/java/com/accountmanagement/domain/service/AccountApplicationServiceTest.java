package com.accountmanagement.domain.service;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.core.valueobject.AccountId;
import com.accountmanagement.domain.core.valueobject.AccountType;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.service.create.CreateAccountCommand;
import com.accountmanagement.domain.service.create.CreateAccountResponse;
import com.accountmanagement.domain.service.create.CreateCustomerCommand;
import com.accountmanagement.domain.service.create.CreateCustomerResponse;
import com.accountmanagement.domain.service.mapper.CustomerDataMapper;
import com.accountmanagement.domain.service.port.input.handler.AccountHandler;
import com.accountmanagement.domain.service.port.input.handler.CustomerHandler;
import com.accountmanagement.domain.service.port.input.service.AccountApplicationService;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = AccountManagementTestConfig.class)
public class AccountApplicationServiceTest {
    @Autowired
    private AccountApplicationService accountApplicationService;
    @Autowired
    private CustomerHandler customerHandler;
    @Autowired
    private AccountHandler accountHandler;
    @Autowired
    private CustomerDataMapper customerDataMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private final UUID CUSTOMER_ID = UUID.fromString("52245de8-6053-41d7-b739-6b66664ff1ce");
    private final UUID ACCOUNT_ID = UUID.fromString("ca9c5863-67a4-4711-8ccb-a3d2c4831e1c");


    @Test
    public void testCreateCustomer() {
        String customerName = "SJA";
        Customer customer = Customer.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .name(customerName)
                .build();
        CreateCustomerCommand command = new CreateCustomerCommand(customerName);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CreateCustomerResponse createCustomerResponse = accountApplicationService.createCustomer(command);
        assertEquals(createCustomerResponse.customerId(), CUSTOMER_ID);
        assertEquals(createCustomerResponse.customerName(), customerName);
    }

    @Test
    public void testCreateCustomerWithInvalidData() {
        CreateCustomerCommand command = new CreateCustomerCommand(null);
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> accountApplicationService.createCustomer(command));
        assertEquals(exception.getMessage(), "createCustomer.createCustomerCommand.customerName: must not be null");
    }

    @Test
    public void createAccount() {
        String customerName = "SJA";
        BigDecimal amount = new BigDecimal("100.00");
        Customer customer = Customer.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .name(customerName)
                .build();
        Account account = Account.builder()
                .customer(customer)
                .accountId(new AccountId(ACCOUNT_ID))
                .balance(Money.createWithScale(amount))
                .accountType(AccountType.SAVINGS)
                .build();

        CreateAccountCommand createAccountCommand =  CreateAccountCommand
                .builder()
                .customerId(CUSTOMER_ID)
                .accountType(AccountType.SAVINGS.name())
                .amount(amount)
                .build();

        when(customerRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        CreateAccountResponse createAccountResponse = accountApplicationService.createAccount(createAccountCommand);

        assertEquals(createAccountResponse.accountId(), ACCOUNT_ID);
        assertEquals(createAccountResponse.accountType(), AccountType.SAVINGS.name());
        assertEquals(createAccountResponse.balance(), amount);
        assertEquals(createAccountResponse.customerId(), CUSTOMER_ID);
    }

    @Test
    public void createAccountWithoutCustomer() {
        BigDecimal amount = new BigDecimal("100.00");
        CreateAccountCommand createAccountCommand =  CreateAccountCommand
                .builder()
                .customerId(CUSTOMER_ID)
                .accountType(AccountType.SAVINGS.name())
                .amount(amount)
                .build();
        when(customerRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> accountApplicationService.createAccount(createAccountCommand));
        assertEquals(exception.getMessage(), "Could not find Customer with customerId: " + CUSTOMER_ID);

    }

}
