package com.wefox.payment.processor.config.services;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.components.OfflineAccountServiceImpl;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("processor/external/repository/service")
public class OfflineProcessorConfiguration {

    @Bean("processor/offline/service")
    public IAccountService offlineAccountService(
            @Qualifier("processor/external/repository/service") final IAccountRepository accountRepository
    )
    {
        return new OfflineAccountServiceImpl(accountRepository);
    }

}
