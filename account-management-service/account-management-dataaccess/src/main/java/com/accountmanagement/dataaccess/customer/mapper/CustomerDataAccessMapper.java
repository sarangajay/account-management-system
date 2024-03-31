package com.accountmanagement.dataaccess.customer.mapper;

import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.dataaccess.customer.repository.CustomerJPARepository;
import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomerDataAccessMapper {

    private final CustomerJPARepository customerJPARepository;

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity
                .builder()
                .id(customer.getCustomerId().getValue())
                .name(customer.getName())
                .build();
    }

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer
                .builder()
                .customerId(new CustomerId(customerEntity.getId()))
                .name(customerEntity.getName())
                .build();
    }
}
