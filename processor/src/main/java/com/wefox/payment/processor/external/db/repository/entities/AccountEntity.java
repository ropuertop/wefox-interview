package com.wefox.payment.processor.external.db.repository.entities;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
@Table(name = "accounts", schema = "public")
public class AccountEntity implements IDBMapper<Account> {

    @Id
    @Column(name = "account_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birthdate", columnDefinition = "DATE")
    private LocalDateTime birthdate;

    @Column(name = "last_payment_date")
    private LocalDateTime lastPaymentDate;

    @Column(name = "created_on")
    private LocalDateTime createdAt;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<PaymentEntity> payments = new HashSet<>();

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

        var newAccount = new AccountEntity();

        newAccount.accountId(domainModel.getId().intValue());
        newAccount.name(domainModel.getName());
        newAccount.birthdate(domainModel.getBirthDate());
        newAccount.createdAt(domainModel.getCreatedAt());
        newAccount.lastPaymentDate(domainModel.findLastPaymentDate().orElse(null));
        newAccount.email(domainModel.getEmail());

        return newAccount;
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
