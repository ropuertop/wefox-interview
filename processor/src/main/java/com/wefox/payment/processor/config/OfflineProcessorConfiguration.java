package com.wefox.payment.processor.config;

import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.service.impl.OfflineAccountServiceImpl;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfflineProcessorConfiguration {

    @Bean("processor/offline/service")
    public IAccountService offlineAccountService(
            @Qualifier("processor/external/repository/service") final IAccountRepository accountRepository
    )
    {
        return new OfflineAccountServiceImpl(accountRepository);
    }

}
