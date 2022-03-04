package com.wefox.payment.processor.config.events;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.components.PaymentProcessorImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("processor/offline/service")
public class OfflinePaymentProcessor {

    @Bean("processor/offline")
    public IPaymentProcessor paymentProcessor(
            @Qualifier("processor/offline/service") final IAccountService accountService
            )
    {
        return new PaymentProcessorImpl(accountService);
    }
}
