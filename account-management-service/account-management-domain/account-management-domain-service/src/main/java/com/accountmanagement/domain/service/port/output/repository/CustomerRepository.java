package com.accountmanagement.domain.service.port.output.repository;

import com.accountmanagement.domain.core.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByCustomerId(UUID customerId);
}
