package com.wefox.payment.processor.core.service.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.verification.IPaymentVerificator;
import com.wefox.payment.processor.external.db.IAccountRepository;

import java.util.Arrays;

public class OnlineAccountServiceImpl extends AbstractAccountServiceImpl {

    private final IPaymentVerificator paymentVerificator;

    /**
     * Parameterized constructor with the bean injections associated to the {@link OnlineAccountServiceImpl}
     *
     * @param paymentVerificator the current {@link IPaymentVerificator} bean component
     * @param accountRepository the current {@link IAccountRepository} bean implementation
     */
    public OnlineAccountServiceImpl(final IPaymentVerificator paymentVerificator,
                                    final IAccountRepository accountRepository) {
        super(accountRepository);
        this.paymentVerificator = paymentVerificator;
    }

    @Override
    public final Account addNewPayments(Account account, Payment... payments) {

        // filtering by the third party validator
        final var validPayments = Arrays.stream(payments)
                .filter(this.paymentVerificator::validatePayment)
                .toArray(Payment[]::new);

        // updating the new payments
        account.addNewPayments(validPayments);

        // storing and returning the updated account
        return this.accountRepository.save(account);
    }

}
