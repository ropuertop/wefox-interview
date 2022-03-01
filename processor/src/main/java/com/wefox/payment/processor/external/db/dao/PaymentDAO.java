package com.wefox.payment.processor.external.db.dao;

import com.wefox.payment.processor.external.db.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface will provide the communication with the database
 *
 * @author ropuertop
 */
@Repository
public interface PaymentDAO extends JpaRepository<PaymentEntity, String> {
}
