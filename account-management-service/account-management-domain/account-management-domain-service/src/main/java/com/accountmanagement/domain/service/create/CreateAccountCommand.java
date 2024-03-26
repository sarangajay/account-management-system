package com.accountmanagement.domain.service.create;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountCommand(@NotNull UUID customerId,
                                   @NotNull String accountType,
                                   @NotNull BigDecimal amount) {
}
