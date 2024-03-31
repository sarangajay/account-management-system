package com.accountmanagement.domain.core.valueobject;

import com.accountmanagement.domain.core.exception.AccountDomainException;

public enum TransactionType {
    DEBIT,
    CREDIT;

    public static TransactionType isValidTransactionType(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new AccountDomainException("Invalid Account Movement Type: " + value);
    }
}
