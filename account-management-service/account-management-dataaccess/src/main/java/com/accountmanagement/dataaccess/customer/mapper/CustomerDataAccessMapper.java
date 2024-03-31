package com.accountmanagement.dataaccess.customer.mapper;

import com.accountmanagement.dataaccess.account.mapper.AccountDataAccessMapper;
import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerDataAccessMapper {

    private final AccountDataAccessMapper accountDataAccessMapper;

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity
                .builder()
                .id(customer.getCustomerId().getValue())
                .name(customer.getName())
                .accounts(accountDataAccessMapper.accountsToAccountEntities(customer.getAccounts()))
                .build();
    }

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer
                .builder()
                .customerId(new CustomerId(customerEntity.getId()))
                .name(customerEntity.getName())
                .accounts(accountDataAccessMapper.accountEntitiesToAccounts(customerEntity.getAccounts()))
                .build();
    }
}
