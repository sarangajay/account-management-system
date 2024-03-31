package com.accountmanagement.dataaccess.account.adapter;

import com.accountmanagement.dataaccess.account.mapper.AccountDataAccessMapper;
import com.accountmanagement.dataaccess.account.repository.AccountJPARepository;
import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.dataaccess.customer.repository.CustomerJPARepository;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.entity.Transaction;
import com.accountmanagement.domain.core.exception.CustomerNotFoundException;
import com.accountmanagement.domain.core.valueobject.*;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import com.accountmanagement.domain.service.port.output.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJPARepository accountJPARepository;
    private final CustomerJPARepository customerJPARepository;
    private final AccountDataAccessMapper accountDataAccessMapper;
    private final TransactionRepository transactionRepository;
    @Override
    public Account save(Account account) {
        Optional<CustomerEntity> customerEntity = customerJPARepository.findById(account.getCustomer().getCustomerId().getValue());
        if(customerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found for customerId:" +  account.getCustomer().getCustomerId().getValue());
        }
        return accountDataAccessMapper.accountEntityToAccount(
                accountJPARepository.save(accountDataAccessMapper.accountToAccountEntity(account, customerEntity.get())));
    }

    @Override
    public Optional<Account> findByAccountId(UUID accountId) {
        return accountJPARepository.findById(accountId)
                .map(e ->
                        Account
                                .builder()
                                .accountId(new AccountId(e.getId()))
                                .accountType(e.getAccountType())
                                .balance(Money.createWithScale(e.getBalance()))
                                .customer(Customer
                                        .builder()
                                        .customerId(new CustomerId(e.getCustomer().getId()))
                                        .build())
                                .build()
                );
    }

}
