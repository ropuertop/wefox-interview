package com.wefox.payment.processor.config.client;

import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verificator.components.PaymentVerificatorImpl;
import com.wefox.payment.processor.external.client.verificator.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verificator.connection.components.PaymentVerificatorConnectionImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsVerificatorProcessorConfiguration {

    @Bean("processor/external/client/connection")
    public IPaymentVerificatorConnection paymentVerificatorConnection()
    {
        return new PaymentVerificatorConnectionImpl();
    }

    @Bean("processor/external/client/verification")
    public IPaymentVerificator paymentVerificator(
            @Qualifier("processor/external/client/connection") final IPaymentVerificatorConnection paymentVerificatorConnection
            ) {
        return new PaymentVerificatorImpl(paymentVerificatorConnection);
    }
}
