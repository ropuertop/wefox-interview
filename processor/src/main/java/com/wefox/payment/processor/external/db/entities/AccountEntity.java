package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@Table(name = "accounts", schema = "payments")
public class AccountEntity implements IDBMapper<Account> {

    @Id
    @Column(name = "account_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birthdate")
    private final LocalDateTime birthdate;

    @Column(name = "last_payment_date")
    private LocalDateTime lastPaymentDate;

    @Column(name = "created_on")
    private final LocalDateTime createdAt;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn
    private Set<PaymentEntity> payments;

    @Override
    public final Account map() {
        return Account.builder()
                .createdAt(this.createdAt)
                .email(this.email)
                .name(this.name)
                .birthDate(this.birthdate)
                .id(Long.valueOf(this.accountId))
                .payments(this.payments.stream().map(PaymentEntity::map).collect(Collectors.toSet()))
                .build();
    }

    /**
     * This method is in charge of mapping the domain {@link Account} model into the {@link AccountEntity} model
     *
     * @param domainModel the {@link Account} domain model
     * @return a new {@link AccountEntity} filled with the domain {@link Account} data
     */
    public static AccountEntity map(final Account domainModel) {
        return AccountEntity.builder()
                .accountId(domainModel.getId().intValue())
                .name(domainModel.getName())
                .birthdate(domainModel.getBirthDate())
                .createdAt(domainModel.getCreatedAt())
                .lastPaymentDate(domainModel.findLastPaymentDate().orElse(null))
                .email(domainModel.getEmail())
                .build();
    }
}
