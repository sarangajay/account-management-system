package com.accountmanagement.domain.core.entity;

import com.accountmanagement.domain.core.entity.common.AggregateRoot;
import com.accountmanagement.domain.core.valueobject.AccountId;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.valueobject.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Account extends AggregateRoot<AccountId> {
    private final AccountId accountId;
    private final AccountType accountType;
    private final Customer customer;
    private Money balance;
    private List<Transaction> transactions;


}
