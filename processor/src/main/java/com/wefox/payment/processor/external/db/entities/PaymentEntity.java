package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "payments", schema = "payments")
@NoArgsConstructor
public class PaymentEntity implements IDBMapper<Payment> {

    @Id
    @Column(name = "payment_id", updatable = false)
    private String paymentId;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = AccountEntity.class, optional = false)
    private AccountEntity account;

    @Override
    public final Payment map() {
        return Payment.builder()
                .account(this.account.map())
                .amount(BigDecimal.valueOf(this.amount))
                .createdAt(this.createdAt)
                .creditCard(this.creditCard)
                .id(UUID.fromString(this.paymentId))
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
                .paymentId(domainModel.getId().toString())
                .paymentType(domainModel.getType().name())
                .creditCard(domainModel.getCreditCard())
                .amount(domainModel.getAmount().intValue())
                .createdAt(domainModel.getCreatedAt())
                .build();
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentEntity that = (PaymentEntity) o;
        return paymentId != null && Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
