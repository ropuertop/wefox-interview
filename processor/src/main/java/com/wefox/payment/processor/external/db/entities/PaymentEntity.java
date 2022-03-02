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
@Entity
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

        var newPayment = new PaymentEntity();

        newPayment.setPaymentId(domainModel.getId().toString());
        newPayment.setPaymentType(domainModel.getType().name());
        newPayment.setCreditCard(domainModel.getCreditCard());
        newPayment.setAmount(domainModel.getAmount());
        newPayment.setCreatedAt(domainModel.getCreatedAt());

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
