package com.accountmanagement.domain.core.valueobject;

import com.accountmanagement.domain.core.exception.AccountDomainException;

public enum AccountType {
    SAVINGS;

    public static AccountType isValidAccountType(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new AccountDomainException("Invalid AccountType: " + value);
    }
}
