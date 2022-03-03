package com.wefox.payment.processor.external.db.repository.dao;

import com.wefox.payment.processor.external.db.repository.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface will provide the communication with the database
 *
 * @author ropuertop
 */
@Repository("processor/external/repository/dao/accounts")
public interface AccountDAO extends JpaRepository<AccountEntity, Integer> {

}
