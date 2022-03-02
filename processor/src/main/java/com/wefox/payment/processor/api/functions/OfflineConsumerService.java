package com.wefox.payment.processor.api.functions;

import com.wefox.payment.processor.api.model.types.OfflinePaymentDTO;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.impl.OfflineAccountServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * This class is in charge of consume every message of kafka offline stream
 *
 * @author ropuertop
 */
@Log4j2
@Configuration
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

    @Bean
    public Consumer<OfflinePaymentDTO> offlinePayment() {

        return offlinePaymentDTO -> {

            log.info("(offline) -> consuming the [{}] payment", offlinePaymentDTO.getPaymentId());

            // finding the related payment account
            final var relatedAccount =  this.accountService
                    .getAccount(offlinePaymentDTO.getAccountId())
                    .filter(Account::isValid);

            // if the account is present, we will try to update its payments
            relatedAccount.ifPresent(account -> {
                final var payment = offlinePaymentDTO.map(account);

                // if the payment is valid, we update
                if(Boolean.TRUE.equals(payment.isValid()))
                {
                    this.accountService.addNewPayments(account, payment);
                }

            });
        };
    }
}
