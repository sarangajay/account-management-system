package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerCommand(@NotNull @NotEmpty String customerName) {
}
