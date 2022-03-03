package com.wefox.payment.processor.events.components;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.components.OfflineAccountServiceImpl;
import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.aspects.LogSystem;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OfflinePaymentProcessorImpl implements IPaymentProcessor {

    /**
     * The {@link IAccountService} implementation
     *
     * @see OfflineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link OfflinePaymentProcessorImpl} beans implementation
     *
     * @param accountService the {@link IAccountService} implementation
     */
    public OfflinePaymentProcessorImpl(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @LogSystem
    public void processPayment(final PaymentDTO offlinePaymentDTO) {

        log.info("(offline) -> consuming the [{}] payment", offlinePaymentDTO.getPaymentId());

        // finding the related payment account
        final var relatedAccount =  accountService
                .getAccount(offlinePaymentDTO.getAccountId());

        // if the account is present, we will try to update its payments
        relatedAccount.ifPresent(account -> {

            // mapping the received payment dto into domain model
            final var payment = offlinePaymentDTO.map(account);

            // if the payment is valid, we update
            final var persistedAccount = accountService.addNewPayments(account, payment);

            log.info("(offline) -> consumed the [{}] payment: [{}]", offlinePaymentDTO.getPaymentId(), persistedAccount);
        });
    }
}
