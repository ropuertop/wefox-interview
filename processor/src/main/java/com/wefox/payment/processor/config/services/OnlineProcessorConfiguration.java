package com.wefox.payment.processor.config.services;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.components.OnlineAccountServiceImpl;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn({"processor/external/client/verificator", "processor/external/repository/service"})
public class OnlineProcessorConfiguration {

    @Bean("processor/online/service")
    public IAccountService onlineAccountService(
            @Qualifier("processor/external/client/verificator") final IPaymentVerificator paymentVerificator,
            @Qualifier("processor/external/repository/service") final IAccountRepository accountRepository
    )
    {
        return new OnlineAccountServiceImpl(paymentVerificator, accountRepository);
    }

}
