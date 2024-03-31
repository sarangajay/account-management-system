package com.accountmanagement.dataaccess.transaction.entity;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import com.accountmanagement.domain.core.valueobject.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    private UUID id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && transactionType == that.transactionType && Objects.equals(transactionTime, that.transactionTime) && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
