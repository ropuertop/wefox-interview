package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "accounts", schema = "payments")
@NoArgsConstructor
public class AccountEntity implements IDBMapper<Account> {

    @Id
    @Column(name = "account_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Column(name = "last_payment_date")
    private LocalDateTime lastPaymentDate;

    @Column(name = "created_on")
    private LocalDateTime createdAt;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn
    @ToString.Exclude
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountEntity that = (AccountEntity) o;
        return accountId != null && Objects.equals(accountId, that.accountId);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
