package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentEntity implements IDBMapper<Payment, PaymentEntity> {

    private final UUID paymentId;
    private final Integer accountId;
    private final String paymentType;
    private final String creditCard;
    private final Integer amount;
    private final LocalDateTime createdAt;

    @Override
    public final PaymentEntity map(final Payment domainModel) {
        return PaymentEntity.builder()
                .paymentId(domainModel.getId())
                .accountId(domainModel.getAccount().getId().intValue())
                .paymentType(domainModel.getType().name())
                .creditCard(domainModel.getCreditCard())
                .amount(domainModel.getAmount().intValue())
                .createdAt(domainModel.getCreatedAt())
                .build();
    }
}
