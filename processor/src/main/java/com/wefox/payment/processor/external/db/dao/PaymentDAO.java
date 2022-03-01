package com.wefox.payment.processor.external.db.dao;

import com.wefox.payment.processor.external.db.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDAO extends JpaRepository<PaymentEntity, String> {
}
