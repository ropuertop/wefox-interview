package com.wefox.payment.processor.config.client;

import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verificator.components.PaymentVerificatorImpl;
import com.wefox.payment.processor.external.client.verificator.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verificator.connection.components.PaymentVerificatorConnectionImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("processor/external/client/logs")
public class ClientsVerificatorProcessorConfiguration {

    @Bean("processor/external/client/verification/connection")
    public IPaymentVerificatorConnection paymentVerificatorConnection()
    {
        return new PaymentVerificatorConnectionImpl();
    }

    @Bean("processor/external/client/verification")
    public IPaymentVerificator paymentVerificator(
            @Qualifier("processor/external/client/verification/connection") final IPaymentVerificatorConnection paymentVerificatorConnection,
            @Qualifier("processor/external/client/logs") final ILogSystem logSystem
    ) {
        return new PaymentVerificatorImpl(paymentVerificatorConnection, logSystem);
    }
}
