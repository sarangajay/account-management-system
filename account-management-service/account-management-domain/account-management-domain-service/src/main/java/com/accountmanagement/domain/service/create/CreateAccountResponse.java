package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;
@Builder
public record CreateAccountResponse(@NotNull UUID customerId,
                                    @NotNull UUID accountId,
                                    @NotNull String accountType,
                                    @NotNull BigDecimal balance) {
}
