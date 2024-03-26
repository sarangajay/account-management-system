package com.accountmanagement.domain.service.port.input.handler;

import com.accountmanagement.domain.service.create.CreateCustomerCommand;
import com.accountmanagement.domain.service.create.CreateCustomerResponse;
import com.accountmanagement.domain.service.mapper.CustomerDataMapper;
import com.accountmanagement.domain.service.port.output.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class CustomerHandler {

    private final CustomerRepository customerRepository;

    private final CustomerDataMapper customerDataMapper;
    @Transactional
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        return customerDataMapper.createCreateCustomerResponseFromCustomer(
                customerRepository.save(customerDataMapper.createCustomerCommandToCustomer(createCustomerCommand))
        );
    }


}
