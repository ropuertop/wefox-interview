package com.wefox.payment.processor.api.model;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class AbstractPaymentDTO<E extends Payment, T extends Account>{

    protected final String paymentId;
    protected final Integer accountId;
    protected final String paymentType;
    protected final String creditCard;
    protected final Integer amount;
    protected final LocalDateTime createdAt;

    /**
     * This method is in charge of mapping the {@link AbstractPaymentDTO} instantiated
     * into a new {@link Payment} domain model
     *
     * @param account the {@link Account} associated to the {@link Payment}
     * @return a new {@link Payment} domain model filled with the {@link AbstractPaymentDTO} instance
     */
    protected abstract E map(T account);
}
