package com.accountmanagement.domain.core.exception;

public class AccountDomainException extends DomainException{
    public AccountDomainException(String message) {
        super(message);
    }

    public AccountDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
