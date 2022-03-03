package com.wefox.payment.processor.events;

import com.wefox.payment.processor.events.model.PaymentDTO;

public interface IPaymentProcessor {

    void processPayment(final PaymentDTO paymentDTO);
}
