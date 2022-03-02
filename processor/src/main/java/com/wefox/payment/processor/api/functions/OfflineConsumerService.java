package com.wefox.payment.processor.api.functions;

import com.wefox.payment.processor.api.model.PaymentDTO;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.impl.OfflineAccountServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This class is in charge of consume every message of kafka offline stream
 *
 * @author ropuertop
 */
@Log4j2
@Service
@DependsOn("processor/offline/service")
public class OfflineConsumerService {

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
    @Autowired
    public OfflineConsumerService(
            @Qualifier("processor/offline/service") final IAccountService accountService
    ) {
        this.accountService = accountService;
    }

    /**
     * This method is in charge of being the entrypoint of the offline topic messages
     *
     * @return a new {@link Consumer} with the processor behavior
     */
    @Bean
    public Consumer<PaymentDTO> offlinePayment() {

        return offlinePaymentDTO -> {

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
        };
    }
}
