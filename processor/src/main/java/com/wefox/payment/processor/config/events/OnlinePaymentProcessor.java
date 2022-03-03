package com.wefox.payment.processor.config.events;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.components.OnlinePaymentProcessorImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("processor/online/service")
public class OnlinePaymentProcessor {

    @Bean("processor/online")
    public IPaymentProcessor paymentProcessor(
            @Qualifier("processor/online/service") final IAccountService accountService
    )
    {
        return new OnlinePaymentProcessorImpl(accountService);
    }
}
