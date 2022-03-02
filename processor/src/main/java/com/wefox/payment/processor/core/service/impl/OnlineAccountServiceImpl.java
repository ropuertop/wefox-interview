package com.wefox.payment.processor.core.service.impl;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.db.IAccountRepository;

import java.util.Arrays;
import java.util.Optional;

public class OnlineAccountServiceImpl implements IAccountService {

    private final IPaymentVerificator paymentVerificator;
    private final IAccountRepository accountRepository;

    /**
     * Parameterized constructor with the bean injections associated to the {@link OnlineAccountServiceImpl}
     *
     * @param paymentVerificator the current {@link IPaymentVerificator} bean component
     * @param accountRepository the current {@link IAccountRepository} bean implementation
     */
    public OnlineAccountServiceImpl(final IPaymentVerificator paymentVerificator,
                                    final IAccountRepository accountRepository) {
        this.paymentVerificator = paymentVerificator;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account addNewPayments(Account account, Payment... payments) {

        // filtering by the third party validator
        final var validPayments = Arrays.stream(payments)
                .filter(this.paymentVerificator::validatePayment)
                .toArray(Payment[]::new);

        // updating the new payments
        account.addNewPayments(validPayments);

        // storing and returning the updated account
        return this.accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccount(Integer accountId) {
        return this.accountRepository.findById(accountId);
    }
}
