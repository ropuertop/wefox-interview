package com.wefox.payment.processor.config.client;

import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verificator.components.PaymentVerificatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsVerificatorProcessorConfiguration {

    @Bean("processor/external/client/verificator")
    public IPaymentVerificator paymentVerificator()
    {
        return new PaymentVerificatorImpl();
    }
}
