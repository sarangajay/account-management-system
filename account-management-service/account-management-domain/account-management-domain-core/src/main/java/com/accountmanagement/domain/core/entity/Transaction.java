package com.accountmanagement.domain.core.entity;

import com.accountmanagement.domain.core.entity.common.EntityBase;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.core.valueobject.TransactionId;
import com.accountmanagement.domain.core.valueobject.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
@Getter
@Builder
@AllArgsConstructor
public class Transaction extends EntityBase<TransactionId> {
    private final TransactionId transactionId;
    private final Money money;
    private final TransactionType transactionType;
    private final LocalDateTime transactionTime;
    private final Account account;

    public static class TransactionComparator implements Comparator<Transaction> {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o1.getTransactionTime().compareTo(o2.getTransactionTime());
        }
    }
}
