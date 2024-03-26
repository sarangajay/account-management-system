package com.accountmanagement.domain.core.event;

import com.accountmanagement.domain.core.entity.Account;

import java.time.ZonedDateTime;


public record AccountCreatedEvent(Account account, ZonedDateTime createdAt)
        implements DomainEvent<Account> {
}
