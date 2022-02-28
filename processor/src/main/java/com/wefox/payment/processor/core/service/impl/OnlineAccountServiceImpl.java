package com.wefox.payment.processor.core.service.impl;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.external.client.IPaymentVerificator;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Arrays;
import java.util.Optional;

@CacheConfig(cacheNames = "accounts")
public class OnlineAccountServiceImpl implements IAccountService {

    private final IPaymentVerificator paymentVerificator;
    private final IAccountRepository accountRepository;

    public OnlineAccountServiceImpl(final IPaymentVerificator paymentVerificator,
                                    final IAccountRepository accountRepository) {
        this.paymentVerificator = paymentVerificator;
        this.accountRepository = accountRepository;
    }

    @Override
    @Cacheable(key = "#accountId", unless = "#result.hardback")
    public final Optional<Account> addNewPayments(Integer accountId, Payment... payments) {

        // filtering by the third party validator
        final var validPayments = Arrays.stream(payments)
                .filter(this.paymentVerificator::validatePayment)
                .toArray(Payment[]::new);

        return this.accountRepository.findById(accountId)
                .map(account -> account.addNewPayments(validPayments))
                .map(this.accountRepository::save);
    }

    @Override
    public final Optional<Account> getAccount(Integer accountId) {
        return Optional.empty();
    }
}
