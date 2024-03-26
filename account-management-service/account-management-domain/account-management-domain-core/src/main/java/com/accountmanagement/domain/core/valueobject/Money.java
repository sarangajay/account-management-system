package com.accountmanagement.domain.core.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public static Money createWithScale(BigDecimal amount) {
        return new Money(setScale(amount));
    }
    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null && this.amount.compareTo(money.amount()) > 0;
    }

    public Money deposit(Money money) {
        if (this.amount != null) {
            return new Money(setScale(this.amount.add(money.amount())));
        } else {
            return money;
        }
    }

    public Money withdraw(Money money) {
        return new Money(setScale(this.amount.subtract(money.amount)));
    }

    private static BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

}
