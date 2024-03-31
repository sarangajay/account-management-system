package com.accountmanagement.dataaccess.account.entity;

import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.dataaccess.transaction.entity.TransactionEntity;
import com.accountmanagement.domain.core.valueobject.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
@Entity
public class AccountEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(id, that.id) && accountType == that.accountType && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
