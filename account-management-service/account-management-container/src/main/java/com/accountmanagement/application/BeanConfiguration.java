package com.accountmanagement.application;

import com.accountmanagement.domain.core.AccountManagementService;
import com.accountmanagement.domain.core.AccountManagementServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public AccountManagementService accountManagementService() {
        return new AccountManagementServiceImpl();
    }


}
