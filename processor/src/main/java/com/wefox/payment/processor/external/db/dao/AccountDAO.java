package com.wefox.payment.processor.external.db.dao;

import com.wefox.payment.processor.external.db.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<AccountEntity, Integer> {

}
