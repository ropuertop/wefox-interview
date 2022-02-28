package com.wefox.payment.processor.external.db.entities;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.external.db.utils.IDBMapper;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountEntity implements IDBMapper<Account, AccountEntity> {

    private final Integer accountId;
    private String email;
    private final LocalDateTime birthdate;
    private LocalDateTime lastPaymentDate;
    private final LocalDateTime createdAt;

    @Override
    public final AccountEntity map(final Account domainModel) {
        return AccountEntity.builder()
                .accountId(domainModel.getId().intValue())
                .birthdate(domainModel.getBirthDate())
                .createdAt(domainModel.getCreatedAt())
                .lastPaymentDate(domainModel.findLastPaymentDate().orElse(null))
                .email(domainModel.getEmail())
                .build();
    }
}
