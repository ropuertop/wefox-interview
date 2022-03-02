package com.wefox.payment.processor.api.model.types;

import com.wefox.payment.processor.api.model.AbstractPaymentDTO;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class is in charge of defining the DTO received from kafka topic
 *
 * @author ropuertop
 */
public final class OfflinePaymentDTO extends AbstractPaymentDTO<Payment, Account> {

    public OfflinePaymentDTO(final String paymentId,
                             final Integer accountId,
                             final String paymentType,
                             final String creditCard,
                             final Integer amount,
                             final LocalDateTime createdAt) {
        super(paymentId, accountId, paymentType, creditCard, amount, createdAt);
    }

    @Override
    public Payment map(Account account) {
        return Payment.builder()
                .id(UUID.fromString(this.paymentId))
                .type(PaymentType.valueOf(this.paymentType))
                .createdAt(this.createdAt)
                .creditCard(this.creditCard)
                .amount(BigDecimal.valueOf(this.amount))
                .account(account)
                .build();
    }

}
