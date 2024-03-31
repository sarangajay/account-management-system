package com.accountmanagement.dataaccess.account.mapper;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.valueobject.AccountId;
import com.accountmanagement.domain.core.valueobject.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDataAccessMapper {

    public Account accountEntityToAccount(AccountEntity accountEntity) {
        return Account
                .builder()
                .accountId(new AccountId(accountEntity.getId()))
                .accountType(accountEntity.getAccountType())
                .balance(Money.createWithScale(accountEntity.getBalance()))
                .build();
    }

    public List<Account> accountEntitiesToAccounts(List<AccountEntity> accountEntityList) {
        return accountEntityList
                .stream()
                .map(this::accountEntityToAccount)
                .collect(Collectors.toList());
    }

    public AccountEntity accountToAccountEntity(Account account) {
        return AccountEntity
                .builder()
                .id(account.getAccountId().getValue())
                .accountType(account.getAccountType())
                .balance(account.getBalance().amount())
                .build();
    }
    
    public List<AccountEntity> accountsToAccountEntities(List<Account> accounts) {
        return accounts
                .stream()
                .map(this::accountToAccountEntity)
                .collect(Collectors.toList());
    }

}
