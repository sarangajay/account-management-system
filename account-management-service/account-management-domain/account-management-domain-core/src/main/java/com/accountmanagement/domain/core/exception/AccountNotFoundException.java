package com.accountmanagement.domain.core.exception;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}