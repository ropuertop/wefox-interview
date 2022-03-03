package com.wefox.payment.processor.config.client;

import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.components.LogSystemImpl;
import com.wefox.payment.processor.external.client.logs.connection.ILogSystemConnection;
import com.wefox.payment.processor.external.client.logs.connection.components.LogSystemConnectionImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientLogSystemProcessorConfiguration {

    @Bean("processor/external/client/logs/connection")
    public ILogSystemConnection logSystemConnection()
    {
        return new LogSystemConnectionImpl();
    }

    @Bean("processor/external/client/logs")
    public ILogSystem logSystem(
        @Qualifier("processor/external/client/logs/connection") final ILogSystemConnection logSystemConnection
    ) {
        return new LogSystemImpl(logSystemConnection);
    }
}
