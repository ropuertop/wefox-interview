package com.wefox.payment.processor.core.service.impl;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import org.springframework.cache.annotation.CacheConfig;

import javax.transaction.Transactional;
import java.util.Optional;
import com.wefox.payment.processor.external.db.impl.PSQLAccountRepositoryImpl;

/**
 * This class is in charge of defining the {@link IAccountService} for the offline
 * topic paths
 *
 * @author ropuertop
 */
public class OfflineAccountServiceImpl implements IAccountService {

    /**
     * This {@link IAccountRepository} implementation
     *
     * @see PSQLAccountRepositoryImpl
     */
    private final IAccountRepository accountRepository;

    /**
     * Parameterized constructor with the bean injections associated to the {@link OfflineAccountServiceImpl}
     *
     * @param accountRepository the current {@link IAccountRepository} bean implementation
     */
    public OfflineAccountServiceImpl(final IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
//    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public Account addNewPayments(Account account, final Payment... payments) {

        // updating the account with the new received payments
        account.addNewPayments(payments);

        // storing and returning the updated account
        return this.accountRepository.save(account);
    }

    @Override
//    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public Optional<Account> getAccount(final Integer accountId) {
        return this.accountRepository.findById(accountId);
    }
}
