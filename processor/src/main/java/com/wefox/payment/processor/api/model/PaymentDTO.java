package com.wefox.payment.processor.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public final class PaymentDTO{

    @JsonProperty("payment_id")
    private final String paymentId;
    @JsonProperty("account_id")
    private final Integer accountId;
    @JsonProperty("payment_type")
    private final String paymentType;
    @JsonProperty("credit_card")
    private final String creditCard;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    private final Integer amount;
    private final Integer delay;

    public Payment map(Account account) {
        return Payment.builder()
                .id(UUID.fromString(this.paymentId))
                .type(PaymentType.valueOf(this.paymentType.toUpperCase()))
                .createdAt(this.createdAt)
                .creditCard(this.creditCard)
                .amount(BigDecimal.valueOf(this.amount))
                .account(account)
                .build();
    }
}
