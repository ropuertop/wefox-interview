package com.wefox.payment.processor.external.client.verificator.connection;

import com.wefox.payment.processor.external.client.verificator.model.PaymentDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IPaymentVerificatorConnection {

    Boolean checkIfValid(final PaymentDTO payment) throws URISyntaxException, IOException, InterruptedException;
}
