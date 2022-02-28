package com.wefox.payment.processor.core.service.impl;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

@CacheConfig(cacheNames = "accounts")
public class OfflineAccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;

    public OfflineAccountServiceImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public final Optional<Account> addNewPayments(final Integer accountId, final Payment... payments) {
        return this.accountRepository.findById(accountId)
                .map(account -> account.addNewPayments(payments))
                .map(this.accountRepository::save);
    }

    @Override
    @Cacheable(key = "#accountId")
    public final Optional<Account> getAccount(final Integer accountId) {
        return this.accountRepository.findById(accountId);
    }
}
