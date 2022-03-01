package com.wefox.payment.processor.api.listener.impl;

import com.wefox.payment.processor.api.listener.types.IOfflineConsumer;
import com.wefox.payment.processor.api.model.types.OfflinePaymentDTO;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.service.IAccountService;
import org.springframework.cloud.stream.annotation.StreamListener;
import com.wefox.payment.processor.core.service.impl.OfflineAccountServiceImpl;

/**
 * This class is in charge of consume every message of kafka offline stream
 *
 * @author ropuertop
 */
public class OfflineConsumerImpl implements IOfflineConsumer {

    /**
     * The {@link IAccountService} implementation
     *
     * @see OfflineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link IAccountService} bean implementation
     *
     * @param accountService the {@link IAccountService} implementation
     */
    public OfflineConsumerImpl(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @StreamListener(IOfflineConsumer.OFFLINE_LISTENER)
    public final void getPayments(final OfflinePaymentDTO offlinePaymentDTO) {

        // finding the related payment account
        final var relatedAccount =  this.accountService
                .getAccount(offlinePaymentDTO.getAccountId())
                .filter(Account::isValid);

        // if the account is present, we will try to update its payments
        relatedAccount.ifPresent(account -> {
            final var payment = offlinePaymentDTO.map(account);

            // if the payment is valid, we update
            if(payment.isValid())
            {
                this.accountService.addNewPayments(account, payment);
            }

        });
    }
}
