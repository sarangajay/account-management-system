package com.accountmanagement.dataaccess.customer.adapter;

import com.accountmanagement.dataaccess.account.mapper.AccountDataAccessMapper;
import com.accountmanagement.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.accountmanagement.dataaccess.customer.repository.CustomerJPARepository;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJPARepository customerJPARepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Customer save(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJPARepository.save(customerDataAccessMapper.customerToCustomerEntity(customer))
        );
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        return customerJPARepository.findById(customerId).map(e ->
                Customer
                        .builder()
                        .customerId(new CustomerId(e.getId()))
                        .name(e.getName())
                        .accounts(accountDataAccessMapper.accountEntitiesToAccounts(e.getAccounts()))
                        .build()
        );
    }
}
