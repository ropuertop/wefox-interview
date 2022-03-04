package com.wefox.payment.processor.events.components;

import com.wefox.payment.processor.aspects.LogSystem;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.components.OfflineAccountServiceImpl;
import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.model.PaymentDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PaymentProcessorImpl implements IPaymentProcessor {

    /**
     * The {@link IAccountService} implementation
     *
     * @see OfflineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link PaymentProcessorImpl} beans implementation
     *
     * @param accountService the {@link IAccountService} implementation
     */
    public PaymentProcessorImpl(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @LogSystem
    public void processPayment(final PaymentDTO offlinePaymentDTO) {

        log.info("({}) -> consuming the [{}] payment", offlinePaymentDTO.getPaymentType(), offlinePaymentDTO.getPaymentId());

        // finding the related payment account
        final var relatedAccount = accountService
                .getAccount(offlinePaymentDTO.getAccountId());

        // if the account is present, we will try to update its payments
        relatedAccount.ifPresent(account -> accountService.addNewPayments(account, offlinePaymentDTO.map(account)));

        log.info("({}) -> consumed the [{}] payment", offlinePaymentDTO.getPaymentType(), offlinePaymentDTO.getPaymentId());
    }
}
