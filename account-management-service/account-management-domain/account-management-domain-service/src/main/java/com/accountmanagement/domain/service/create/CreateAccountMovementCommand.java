package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;
@Builder
public record CreateAccountMovementCommand(@NotNull UUID accountId,
                                           @NotNull String movementType,
                                           @NotNull BigDecimal amount) {
}
