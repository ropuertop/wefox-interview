package com.wefox.payment.processor.external.db.impl;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.dao.AccountDAO;
import com.wefox.payment.processor.external.db.dao.PaymentDAO;
import com.wefox.payment.processor.external.db.entities.AccountEntity;

import java.util.Optional;
import java.util.stream.Collectors;

import com.wefox.payment.processor.external.db.entities.PaymentEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is an implementation of the {@link IAccountRepository} for the PostgreSQL database driver
 *
 * @author ropuertop
 */
@Log4j2
public class PSQLAccountRepositoryImpl implements IAccountRepository {

    /**
     * The {@link AccountDAO} {@link JpaRepository} that will be injected.
     */
    private final AccountDAO accountDAO;
    private final PaymentDAO paymentDAO;

    /**
     * Parameterized constructor with the bean injections associated to the {@link PSQLAccountRepositoryImpl}
     * @param accountDAO the current {@link AccountDAO} bean implementation
     * @param paymentDAO
     */
    public PSQLAccountRepositoryImpl(final AccountDAO accountDAO, PaymentDAO paymentDAO) {
        this.accountDAO = accountDAO;
        this.paymentDAO = paymentDAO;
    }

    @Override
    public final Optional<Account> findById(final Integer accountId) {
        log.info("(PSQLAccountRepositoryImpl) -> (findById): finding the account related with [{}]", accountId);
        return this.accountDAO.findById(accountId).map(AccountEntity::map).filter(Account::isValid);
    }

    @Override
    public final Account save(final Account account) {
        log.info("(PSQLAccountRepositoryImpl) -> (save): saving the account related with [{}] with the payments [{}]", account.getId(), account.getPayments().stream().map(Payment::getId));
        this.paymentDAO.saveAll(account.getPayments().stream()
                .map(PaymentEntity::map)
                .map(paymentEntity -> {
                    paymentEntity.setAccount(AccountEntity.map(account));
                    return paymentEntity;
                })
                .collect(Collectors.toSet()));
        return this.accountDAO.save(AccountEntity.map(account)).map();
    }
}
