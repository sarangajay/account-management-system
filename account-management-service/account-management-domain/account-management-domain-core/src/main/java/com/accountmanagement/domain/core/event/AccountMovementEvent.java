package com.accountmanagement.domain.core.event;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.valueobject.TransactionType;
import lombok.Builder;

import java.time.ZonedDateTime;
@Builder
public record AccountMovementEvent(Account account, TransactionType transactionType, ZonedDateTime createdAt)
        implements DomainEvent<Account> {
}