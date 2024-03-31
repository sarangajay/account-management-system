package com.accountmanagement.application.exception.handler;

import com.accountmanagement.domain.core.exception.AccountDomainException;
import com.accountmanagement.domain.core.exception.AccountNotFoundException;
import com.accountmanagement.domain.core.exception.CustomerNotFoundException;
import com.accountmanagement.domain.core.exception.DomainException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {AccountDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(AccountDomainException accountDomainException) {
        log.error(accountDomainException.getMessage(), accountDomainException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(accountDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(AccountNotFoundException accountDomainException) {
        log.error(accountDomainException.getMessage(), accountDomainException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(accountDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(CustomerNotFoundException accountDomainException) {
        log.error(accountDomainException.getMessage(), accountDomainException);
        return ErrorDTO
                .builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(accountDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ValidationException exception) {
        if (exception instanceof ConstraintViolationException) {
            String errors = extractValidationException((ConstraintViolationException) exception);
            log.error(errors, exception);
            return ErrorDTO
                    .builder()
                    .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .message(errors)
                    .build();
        } else {
            String errorMsg = exception.getMessage();
            log.error(errorMsg, exception);
            return ErrorDTO
                    .builder()
                    .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .message(errorMsg)
                    .build();
        }
    }

    private String extractValidationException(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }
}
