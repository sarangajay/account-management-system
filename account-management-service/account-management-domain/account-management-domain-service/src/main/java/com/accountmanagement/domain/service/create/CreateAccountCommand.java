package com.accountmanagement.domain.service.create;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;
@Builder
public record CreateAccountCommand(@NotNull UUID customerId,
                                   @NotNull String accountType,
                                   @NotNull BigDecimal amount) {
}
