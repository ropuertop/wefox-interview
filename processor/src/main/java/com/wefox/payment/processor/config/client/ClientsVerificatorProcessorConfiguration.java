package com.wefox.payment.processor.config.client;

import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.verification.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verification.components.PaymentVerificatorImpl;
import com.wefox.payment.processor.external.client.verification.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verification.connection.components.PaymentVerificatorConnectionImpl;
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
