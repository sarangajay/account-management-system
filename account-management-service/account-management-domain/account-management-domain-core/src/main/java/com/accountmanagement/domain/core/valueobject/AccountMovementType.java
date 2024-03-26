package com.accountmanagement.domain.core.valueobject;

import com.accountmanagement.domain.core.exception.AccountDomainException;

public enum AccountMovementType {
    DEPOSIT, WITHDRAW;

    public static AccountMovementType isValidAccountMovementType(String value) {
        for (AccountMovementType type : AccountMovementType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new AccountDomainException("Invalid Account Movement Type: " + value);
    }
}
