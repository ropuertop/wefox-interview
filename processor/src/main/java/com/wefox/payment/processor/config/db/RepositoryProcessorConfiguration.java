package com.wefox.payment.processor.config.db;

import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.repository.dao.AccountDAO;
import com.wefox.payment.processor.external.db.repository.dao.PaymentDAO;
import com.wefox.payment.processor.external.db.components.JPAAccountRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryProcessorConfiguration {

    @Bean("processor/external/repository/service")
    public IAccountRepository accountRepository(
            @Qualifier("processor/external/repository/dao/accounts") final AccountDAO accountDAO,
            @Qualifier("processor/external/repository/dao/payments") final PaymentDAO paymentDAO
    ) {
        return new JPAAccountRepositoryImpl(accountDAO, paymentDAO);
    }
}
