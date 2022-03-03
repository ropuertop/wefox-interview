package com.wefox.payment.processor.external.client.verificator.components;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;

public class PaymentVerificatorImpl implements IPaymentVerificator {

    @Override
    public final Boolean validatePayment(Payment payment) {
        return true;
    }
}
