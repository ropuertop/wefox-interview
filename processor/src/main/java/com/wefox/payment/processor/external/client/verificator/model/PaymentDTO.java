package com.wefox.payment.processor.external.client.verificator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.payment.processor.core.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

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
    private final Integer amount;

    public static PaymentDTO map(final Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.getId().toString())
                .accountId(payment.getAccount().getId().intValue())
                .paymentType(payment.getType().name().toLowerCase())
                .creditCard(payment.getCreditCard())
                .amount(payment.getAmount().intValue())
                .build();
    }
}

