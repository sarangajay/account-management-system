package com.accountmanagement.domain.core.entity;

import com.accountmanagement.domain.core.entity.common.EntityBase;
import com.accountmanagement.domain.core.valueobject.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Customer extends EntityBase<CustomerId> {
    private final CustomerId customerId;
    private final String name;
    private List<Account> accounts;

}
