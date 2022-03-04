package com.wefox.payment.processor.core.service.components;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.core.service.IAccountService;

/**
 * This class is in charge of defining the {@link IAccountService} for the offline
 * topic paths
 *
 * @author ropuertop
 */
public class OfflineAccountServiceImpl extends AbstractAccountServiceImpl {

    /**
     * Parameterized constructor with the bean injections associated to the {@link OfflineAccountServiceImpl}
     *
     * @param accountRepository the current {@link IAccountRepository} bean implementation
     */
    public OfflineAccountServiceImpl(final IAccountRepository accountRepository) {
        super(accountRepository);
    }

}
