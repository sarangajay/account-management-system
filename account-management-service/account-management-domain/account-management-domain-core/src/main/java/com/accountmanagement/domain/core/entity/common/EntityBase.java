package com.accountmanagement.domain.core.entity.common;

import lombok.Data;

@Data
public abstract class EntityBase<ID> {

    private ID id;

}
