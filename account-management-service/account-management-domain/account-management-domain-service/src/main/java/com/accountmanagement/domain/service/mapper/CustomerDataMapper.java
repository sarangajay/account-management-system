package com.accountmanagement.domain.service.mapper;

import com.accountmanagement.domain.core.entity.Customer;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import com.accountmanagement.domain.service.create.CreateCustomerCommand;
import com.accountmanagement.domain.service.create.CreateCustomerResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataMapper {

    public Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommand) {
        return Customer
                .builder()
                .customerId(new CustomerId(UUID.randomUUID()))
                .name(createCustomerCommand.customerName())
                .build();
    }

    public CreateCustomerResponse createCreateCustomerResponseFromCustomer(Customer customer) {
        return CreateCustomerResponse
                .builder()
                .customerId(customer.getCustomerId().getValue())
                .customerName(customer.getName())
                .build();
    }
}
