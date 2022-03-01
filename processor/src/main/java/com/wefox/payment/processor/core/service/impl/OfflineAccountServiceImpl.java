package com.wefox.payment.processor.core.service.impl;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import javax.transaction.Transactional;
import java.util.Optional;

@CacheConfig(cacheNames = "accounts")
public class OfflineAccountServiceImpl implements IAccountService {

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
    @Transactional
    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public final Optional<Account> addNewPayments(final Integer accountId, final Payment... payments) {

        // updating the account with the new received payments
        return this.accountRepository.findById(accountId)
                .map(account -> account.addNewPayments(payments))
                .filter(Account::isValid)
                .map(this.accountRepository::save);
    }

    @Override
    @Transactional
    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public final Optional<Account> getAccount(final Integer accountId) {
        return this.accountRepository.findById(accountId);
    }
}
