package com.wefox.payment.processor.external.client;

import com.wefox.payment.processor.core.model.Payment;

public interface IPaymentVerificator {

    /**
     * This method is in charge of validate by third party services the validity of the passed {@link Payment}
     * @param payment the {@link Payment} that we want to save
     * @return TRUE if the third party service has validated the {@link Payment}
     */
    Boolean validatePayment(final Payment payment);
}
