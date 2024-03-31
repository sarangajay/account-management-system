package com.accountmanagement.dataaccess.account.adapter;

import com.accountmanagement.dataaccess.account.mapper.AccountDataAccessMapper;
import com.accountmanagement.dataaccess.account.repository.AccountJPARepository;
import com.accountmanagement.domain.core.entity.Account;
import com.accountmanagement.domain.core.valueobject.AccountId;
import com.accountmanagement.domain.core.valueobject.Money;
import com.accountmanagement.domain.service.port.output.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJPARepository accountJPARepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Account save(Account account) {
        return accountDataAccessMapper.accountEntityToAccount(
                accountJPARepository.save(accountDataAccessMapper.accountToAccountEntity(account)));
    }

    @Override
    public Optional<Account> findByAccountId(UUID accountId) {
        return accountJPARepository.findById(accountId)
                .map(e ->
                        Account
                                .builder()
                                .accountId(new AccountId(e.getId()))
                                .accountType(e.getAccountType())
                                .balance(Money.createWithScale(e.getBalance()))
                                .build()
                );
    }

}
