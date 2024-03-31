package com.accountmanagement.dataaccess.account.entity;

import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.domain.core.valueobject.AccountType;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntityId implements Serializable {

    private UUID id;

    private CustomerEntity customer;

    private AccountType accountType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntityId that = (AccountEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && accountType == that.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, accountType);
    }
}
