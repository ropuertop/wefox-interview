package com.wefox.payment.processor.external.db.impl;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.dao.AccountDAO;
import com.wefox.payment.processor.external.db.entities.AccountEntity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is an implementation of the {@link IAccountRepository} for the PostgreSQL database driver
 *
 * @author ropuertop
 */
public class PSQLAccountRepositoryImpl implements IAccountRepository {

    /**
     * The {@link AccountDAO} {@link JpaRepository} that will be injected.
     */
    private final AccountDAO accountDAO;

    /**
     * Parameterized constructor with the bean injections associated to the {@link PSQLAccountRepositoryImpl}
     * @param accountDAO the current {@link AccountDAO} bean implementation
     */
    public PSQLAccountRepositoryImpl(final AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public final Optional<Account> findById(final Integer accountId) {
        // finding the account in the repository and mapping it into domain model
        return this.accountDAO.findById(accountId).map(AccountEntity::map).filter(Account::isValid);
    }

    @Override
    public final Account save(final Account account) {
        return this.accountDAO.save(AccountEntity.map(account)).map();
    }
}
