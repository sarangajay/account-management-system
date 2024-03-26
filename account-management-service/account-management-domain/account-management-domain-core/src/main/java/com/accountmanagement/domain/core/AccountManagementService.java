package com.accountmanagement.domain.core;

import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.event.AccountMovementEvent;

public interface AccountManagementService {
    AccountMovementEvent deposit(Account account, Money money);
    AccountMovementEvent withdraw(Account account, Money money);

}
