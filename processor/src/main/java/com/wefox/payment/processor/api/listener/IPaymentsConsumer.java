package com.wefox.payment.processor.api.listener;

import com.wefox.payment.processor.api.model.AbstractPaymentDTO;

/**
 * This interface is in charge of defining the behavior associated to the 'online' and 'offline'
 * kafka topics
 *
 * @param <T> a {@link AbstractPaymentDTO} child received in kafka topic
 */
@FunctionalInterface
public interface IPaymentsConsumer<T extends AbstractPaymentDTO> {

    /**
     * This method is charge of getting the {@link AbstractPaymentDTO} from kafka topics
     *
     * @param paymentDTO the {@link AbstractPaymentDTO} received from kafka
     */
    void getPayments(T paymentDTO);
}
