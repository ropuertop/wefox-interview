package com.wefox.payment.processor.core.service.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.external.db.IAccountRepository;
import com.wefox.payment.processor.external.db.components.JPAAccountRepositoryImpl;

import java.util.Optional;

public abstract class AbstractAccountServiceImpl implements IAccountService {

    /**
     * The {@link IAccountRepository} implementation
     *
     * @see JPAAccountRepositoryImpl
     */
    protected final IAccountRepository accountRepository;

    protected AbstractAccountServiceImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account addNewPayments(Account account, final Payment... payments) {

        // updating the account with the new received payments
        account.addNewPayments(payments);

        // storing and returning the updated account
        return this.accountRepository.save(account);
    }

    @Override
    public final Optional<Account> getAccount(final Integer accountId) {
        return this.accountRepository.findById(accountId);
    }

}
