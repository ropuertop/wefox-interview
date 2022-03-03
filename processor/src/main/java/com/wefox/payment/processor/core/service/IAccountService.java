package com.wefox.payment.processor.core.service;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;

import java.util.Optional;

/**
 * This interface is in charge of defining the {@link Account} behavior
 * exposed to the API port.
 *
 * @author ropuertop
 */
public interface IAccountService {

    /**
     * This method is in charge of adding new {@link Payment} to the
     * @param account the {@link Account} that we want to update
     * @param payments new {@link Payment} to add into the account
     * @return the updated account
     */
    Account addNewPayments(final Account account, final Payment... payments);

    /**
     * This method is in charge of retrieving the {@link Account} associate to the passed {@link Integer} identifier
     * @param accountId the identifier {@link Integer}
     * @return the {@link Optional} {@link Account} associated to the {@link Integer} identifier
     */
    Optional<Account> getAccount(final Integer accountId);
}
