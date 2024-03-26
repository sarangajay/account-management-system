package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;
@Builder
public record CreateCustomerResponse(@NotNull UUID customerId,
                                     @NotNull String customerName) {
}
