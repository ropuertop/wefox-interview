package com.wefox.payment.processor.config;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.dao.AccountDAO;
import com.wefox.payment.processor.external.db.impl.PSQLAccountRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryProcessorConfiguration {

    @Bean("processor/external/repository/service")
    public IAccountRepository accountRepository(
            @Qualifier("processor/external/repository/dao/accounts") final AccountDAO accountDAO
    ) {
        return new PSQLAccountRepositoryImpl(accountDAO);
    }
}
