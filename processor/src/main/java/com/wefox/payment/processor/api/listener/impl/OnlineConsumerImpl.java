package com.wefox.payment.processor.api.listener.impl;

import com.wefox.payment.processor.api.listener.types.IOnlineConsumer;
import com.wefox.payment.processor.api.model.types.OnlinePaymentDTO;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.service.IAccountService;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * This class is in charge of consume every message of kafka online stream
 *
 * @author ropuertop
 */
public class OnlineConsumerImpl implements IOnlineConsumer {

    /**
     * The {@link IAccountService} implementation
     *
     * @see com.wefox.payment.processor.core.service.impl.OnlineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link OnlineConsumerImpl} bean injections
     * @param accountService the current {@link IAccountService} implementation
     */
    public OnlineConsumerImpl(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @StreamListener(IOnlineConsumer.ONLINE_LISTENER)
    public final void getPayments(final OnlinePaymentDTO onlinePaymentDTO) {

        // finding the related payment account
        final var relatedAccount =  this.accountService
                .getAccount(onlinePaymentDTO.getAccountId())
                .filter(Account::isValid);

        // if the account is present, we will try to update its payments
        relatedAccount.ifPresent(account -> {
            final var payment = onlinePaymentDTO.map(account);

            // if the payment is valid, we update
            if(payment.isValid())
            {
                this.accountService.addNewPayments(account, payment);
            }

        });
    }
}
