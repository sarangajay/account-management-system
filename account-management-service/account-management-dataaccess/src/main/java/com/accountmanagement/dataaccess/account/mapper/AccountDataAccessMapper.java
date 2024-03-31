package com.accountmanagement.dataaccess.account.mapper;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import com.accountmanagement.dataaccess.customer.entity.CustomerEntity;
import com.accountmanagement.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.valueobject.AccountId;
import com.accountmanagement.domain.core.valueobject.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Component
public class AccountDataAccessMapper {

    private final CustomerDataAccessMapper customerDataAccessMapper;
    public Account accountEntityToAccount(AccountEntity accountEntity) {
        return Account
                .builder()
                .accountId(new AccountId(accountEntity.getId()))
                .accountType(accountEntity.getAccountType())
                .balance(Money.createWithScale(accountEntity.getBalance()))
                .customer(customerDataAccessMapper.customerEntityToCustomer(accountEntity.getCustomer()))
                .build();
    }

    public List<Account> accountEntitiesToAccounts(List<AccountEntity> accountEntityList) {
        return accountEntityList
                .stream()
                .map(this::accountEntityToAccount)
                .collect(Collectors.toList());
    }

    public AccountEntity accountToAccountEntity(Account account, CustomerEntity customerEntity) {
        return AccountEntity
                .builder()
                .id(account.getAccountId().getValue())
                .accountType(account.getAccountType())
                .balance(account.getBalance().amount())
                .customer(customerEntity)
                .build();
    }



}
