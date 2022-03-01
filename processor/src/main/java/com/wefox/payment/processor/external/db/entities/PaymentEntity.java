package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "payments", schema = "payments")
public class PaymentEntity implements IDBMapper<Payment> {

    @Id
    @Type(type="uuid-char")
    @Column(name = "payment_id", updatable = false)
    private final UUID paymentId;

    @Column(name = "payment_type", nullable = false)
    private final String paymentType;

    @Column(name = "credit_card")
    private final String creditCard;

    @Column(name = "amount", nullable = false)
    private final Integer amount;

    @Column(name = "created_at")
    private final LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = AccountEntity.class, optional = false)
    private final AccountEntity account;

    @Override
    public final Payment map() {
        return Payment.builder()
                .account(this.account.map())
                .amount(BigDecimal.valueOf(this.amount))
                .createdAt(this.createdAt)
                .creditCard(this.creditCard)
                .id(this.paymentId)
                .type(PaymentType.valueOf(paymentType))
                .build();
    }

    /**
     * This method is in charge of mapping the domain {@link Payment} model into the {@link PaymentEntity} model
     *
     * @param domainModel the {@link Payment} domain model
     * @return a new {@link PaymentEntity} filled with the domain {@link Payment} data
     */
    public static PaymentEntity map(final Payment domainModel) {
        return PaymentEntity.builder()
                .paymentId(domainModel.getId())
                .paymentType(domainModel.getType().name())
                .creditCard(domainModel.getCreditCard())
                .amount(domainModel.getAmount().intValue())
                .createdAt(domainModel.getCreatedAt())
                .build();
    }



}
