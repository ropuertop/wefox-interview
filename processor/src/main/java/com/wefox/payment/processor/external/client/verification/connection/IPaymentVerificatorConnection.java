package com.wefox.payment.processor.external.client.verification.connection;

import com.wefox.payment.processor.external.client.verification.model.PaymentDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IPaymentVerificatorConnection {

    Boolean checkIfValid(final PaymentDTO payment) throws URISyntaxException, IOException, InterruptedException;
}
