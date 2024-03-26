package com.accountmanagement.domain.core;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.event.AccountMovementEvent;
import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.valueobject.TransactionType;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AccountManagementServiceImpl implements AccountManagementService {

    private final String UTC = "UTC";

    @Override
    public AccountMovementEvent deposit(Account account, Money money) {
        if (!money.isGreaterThanZero()) {
            throw new AccountDomainException(String.format("Deposit amount must be positive figure. %s ", money.amount()));
        }
        account.setBalance(account.getBalance().deposit(money));
        return new AccountMovementEvent(account, TransactionType.CREDIT, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public AccountMovementEvent withdraw(Account account, Money money) {
        if (!account.getBalance().isGreaterThan(money)) {
            throw new AccountDomainException(String.format("Withdraw amount: %s must be small than the available balance: %s "
                    ,money.amount(), account.getBalance()));
        }
        account.setBalance(account.getBalance().withdraw(money));
        return new AccountMovementEvent(account, TransactionType.DEBIT, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
