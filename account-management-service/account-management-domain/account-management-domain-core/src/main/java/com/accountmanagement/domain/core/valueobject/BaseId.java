package com.accountmanagement.domain.core.valueobject;

import lombok.Data;

@Data
public abstract class BaseId<T> {

    private final T value;
}
