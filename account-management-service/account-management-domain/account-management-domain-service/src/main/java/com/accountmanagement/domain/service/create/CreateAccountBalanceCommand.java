package com.accountmanagement.domain.service.create;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccountBalanceCommand(@NotNull UUID accountId) {
}
