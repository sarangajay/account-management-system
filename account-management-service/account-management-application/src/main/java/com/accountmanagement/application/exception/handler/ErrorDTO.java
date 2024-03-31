package com.accountmanagement.application.exception.handler;

import com.accountmanagement.domain.core.exception.AccountDomainException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Builder
@Slf4j
@AllArgsConstructor
public record ErrorDTO(String code, String message) {

}
