package com.wefox.payment.processor.config;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.impl.OnlineAccountServiceImpl;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
