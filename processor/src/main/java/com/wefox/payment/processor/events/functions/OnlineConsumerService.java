package com.wefox.payment.processor.events.functions;

import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.core.service.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This class is in charge of consume every message of kafka online stream
 *
 * @author ropuertop
 */
@Log4j2
@Service
@DependsOn("processor/online/service")
public class OnlineConsumerService {

    /**
     * The {@link IAccountService} implementation
     *
     * @see com.wefox.payment.processor.core.service.components.OnlineAccountServiceImpl
     */
    private final IAccountService accountService;

    /**
     * Parameterized constructor with the current {@link OnlineConsumerService} bean injections
     * @param accountService the current {@link IAccountService} implementation
     */
    @Autowired
    public OnlineConsumerService(
            @Qualifier("processor/online/service") final IAccountService accountService
    ) {
        this.accountService = accountService;
    }

    @Bean
    public Consumer<PaymentDTO> onlinePayment() {
        return onlinePaymentDTO -> {

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

        };
    }
}
