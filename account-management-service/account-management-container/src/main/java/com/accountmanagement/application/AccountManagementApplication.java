package com.accountmanagement.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.accountmanagement.dataaccess")
@EntityScan(basePackages = "com.accountmanagement.dataaccess")
@SpringBootApplication(scanBasePackages = "com.accountmanagement")
public class AccountManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementApplication.class, args);
    }
}
