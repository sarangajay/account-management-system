package com.accountmanagement.dataaccess.account.repository;

import com.accountmanagement.dataaccess.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountJPARepository extends JpaRepository<AccountEntity, UUID> {
}
