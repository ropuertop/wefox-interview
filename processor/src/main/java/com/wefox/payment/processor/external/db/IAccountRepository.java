package com.wefox.payment.processor.external.db;

import com.wefox.payment.processor.core.model.Account;

import java.util.Optional;

/**
 * This interface will be in charge of integrating with persistence layers and defining
 * the needed functionality
 *
 * @author ropuertop
 */
public interface IAccountRepository {

    /**
     * This method is in charge of retrieving the account associated to the account identifier passed
     * by parameter
     *
     * @param accountId the {@link Account} identifier
     * @return the {@link Optional} {@link Account} associated to the passed identifier
     */
    Optional<Account> findById(final Integer accountId);

    /**
     * This method is in charge of storing or updating the {@link Account} passed by parameter
     *
     * @param account the {@link Account} that we want to store in database
     * @return the updated/stored {@link Account}
     */
    Account save(final Account account);
}
