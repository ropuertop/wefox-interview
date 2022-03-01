package com.wefox.payment.processor.external.db.dao;

import com.wefox.payment.processor.external.db.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface will provide the communication with the database
 *
 * @author ropuertop
 */
@Repository
public interface AccountDAO extends JpaRepository<AccountEntity, Integer> {

}
