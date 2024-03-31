package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TransactionQuery(@NotNull UUID accountId,
                               @NotNull int limit) {
}
