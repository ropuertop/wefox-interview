package com.wefox.payment.processor.events.components;

import com.wefox.payment.processor.aspects.LogSystem;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.model.PaymentDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Log4j2
public class OnlinePaymentProcessorImpl implements IPaymentProcessor {

    /**
     * The {@link IAccountService} implementation
     *
     * @see com.wefox.payment.processor.core.service.components.OnlineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link OnlinePaymentProcessorImpl} bean injections
     * @param accountService the current {@link IAccountService} implementation
     */
    @Autowired
    public OnlinePaymentProcessorImpl(
            @Qualifier("processor/online/service") final IAccountService accountService
    ) {
        this.accountService = accountService;
    }

    @Override
    @LogSystem
    public void processPayment(final PaymentDTO onlinePaymentDTO) {

        log.info("(online) -> consuming the [{}] payment", onlinePaymentDTO.getPaymentId());

        // finding the related payment account
        final var relatedAccount = accountService
                .getAccount(onlinePaymentDTO.getAccountId());

        // if the account is present, we will try to update its payments
        relatedAccount.ifPresent(account -> {
            final var payment = onlinePaymentDTO.map(account);

            // if the payment is valid, we update
            if(Boolean.TRUE.equals(payment.isValid()))
            {
                accountService.addNewPayments(account, payment);
            }

        });

        log.info("(online) -> consumed the [{}] payment", onlinePaymentDTO.getPaymentId());

    }
}
