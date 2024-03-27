package com.accountmanagement.domain.service;

import com.accountmanagement.domain.core.AccountManagementService;
import com.accountmanagement.domain.core.AccountManagementServiceImpl;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.accountmanagement")
public class AccountManagementTestConfig {

    @Bean
    public AccountRepository accountRepository() {
        return Mockito.mock(AccountRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public TransactionRepository transactionRepository() {
        return Mockito.mock(TransactionRepository.class);
    }

    @Bean
    public AccountManagementService accountManagementService() {
        return new AccountManagementServiceImpl();
    }

}
