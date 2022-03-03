package com.wefox.payment.processor.external.db.repository.entities;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Accessors(fluent = true, chain = true)
@Table(name = "payments", schema = "public")
public class PaymentEntity implements IDBMapper<Payment> {

    @Id
    @Column(name = "payment_id", updatable = false)
    private String paymentId;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "created_on")
    private LocalDateTime createdAt;

    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private AccountEntity account;

    @Override
    public final Payment map() {
        return Payment.builder()
                .amount(this.amount)
                .createdAt(this.createdAt)
                .creditCard(this.creditCard)
                .id(UUID.fromString(this.paymentId))
                .type(PaymentType.valueOf(this.paymentType.toUpperCase()))
                .build();
    }

    /**
     * This method is in charge of mapping the domain {@link Payment} model into the {@link PaymentEntity} model
     *
     * @param domainModel the {@link Payment} domain model
     * @return a new {@link PaymentEntity} filled with the domain {@link Payment} data
     */
    public static PaymentEntity map(final Payment domainModel) {

        var newPayment = new PaymentEntity();

        newPayment.paymentId(domainModel.getId().toString());
        newPayment.paymentType(domainModel.getType().name().toLowerCase());
        newPayment.creditCard(domainModel.getCreditCard());
        newPayment.amount(domainModel.getAmount());
        newPayment.createdAt(domainModel.getCreatedAt());

        return newPayment;
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
