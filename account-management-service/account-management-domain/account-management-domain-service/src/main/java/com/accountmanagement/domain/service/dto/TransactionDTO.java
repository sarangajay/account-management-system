package com.accountmanagement.domain.service.dto;

import com.accountmanagement.domain.core.valueobject.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionDTO(@NotNull UUID transactionId,
                             @NotNull BigDecimal amount,
                             @NotNull TransactionType transactionType,
                             @NotNull LocalDateTime transactionTime) {
}
