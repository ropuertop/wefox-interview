package com.wefox.payment.processor.external.db.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.repository.dao.AccountDAO;
import com.wefox.payment.processor.external.db.repository.dao.PaymentDAO;
import com.wefox.payment.processor.external.db.repository.entities.AccountEntity;

import java.util.Optional;
import java.util.stream.Collectors;

import com.wefox.payment.processor.external.db.repository.entities.PaymentEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is an implementation of the {@link IAccountRepository} for the PostgreSQL database driver
 *
 * @author ropuertop
 */
@Log4j2
public class JPAAccountRepositoryImpl implements IAccountRepository {

    /**
     * The {@link AccountDAO} {@link JpaRepository} that will be injected.
     */
    private final AccountDAO accountDAO;

    /**
     * The {@link PaymentDAO} {@link JpaRepository} that will be injected.
     */
    private final PaymentDAO paymentDAO;

    /**
     * Parameterized constructor with the bean injections associated to the {@link JPAAccountRepositoryImpl}
     * @param accountDAO the current {@link AccountDAO} bean implementation
     * @param paymentDAO the current {@link PaymentDAO} bean implementation
     */
    public JPAAccountRepositoryImpl(final AccountDAO accountDAO, PaymentDAO paymentDAO) {
        this.accountDAO = accountDAO;
        this.paymentDAO = paymentDAO;
    }

    @Override
    public final Optional<Account> findById(final Integer accountId) {
        log.debug("(JPAAccountRepositoryImpl) -> (findById): finding the account related with [{}]", accountId);
        return this.accountDAO.findById(accountId).map(AccountEntity::map);
    }

    @Override
    public final Account save(final Account account) {
        log.debug("(JPAAccountRepositoryImpl) -> (save): saving the account related with [{}] with the payments [{}]", account.getId(), account.getPayments().stream().map(Payment::getId));

        // saving the updated account
        final var persistedAccount = this.accountDAO.save(AccountEntity.map(account));

        // saving every related payment
        this.paymentDAO.saveAll(account.getPayments().stream()
                .map(PaymentEntity::map)
                .map(paymentEntity -> paymentEntity.account(persistedAccount))
                .collect(Collectors.toSet()));

        // returning the updated domain model
        return persistedAccount.map();
    }
}
