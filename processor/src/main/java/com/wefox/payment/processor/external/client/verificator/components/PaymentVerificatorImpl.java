package com.wefox.payment.processor.external.client.verificator.components;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.verificator.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verificator.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verificator.model.PaymentDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpTimeoutException;

@Log4j2
public class PaymentVerificatorImpl implements IPaymentVerificator {

    private final IPaymentVerificatorConnection paymentVerificatorConnection;

    public PaymentVerificatorImpl(IPaymentVerificatorConnection paymentVerificatorConnection) {
        this.paymentVerificatorConnection = paymentVerificatorConnection;
    }

    @Override
    public final Boolean validatePayment(final Payment payment) {
        try
        {
            return this.paymentVerificatorConnection.checkIfValid(PaymentDTO.map(payment));

        }catch (HttpTimeoutException e) {
            log.error("(PaymentVerificatorImpl) -> (validatePayment): There was a timeout for the payment [{}]", payment.getId());
            return false; // TODO: create log
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return false; // TODO: create log
        } catch (InterruptedException e) {
            e.printStackTrace();
            final var status = Thread.interrupted();
            log.warn("(PaymentVerificatorImpl) -> (validatePayment): The thread [{}] has been interrupted? [{}]", Thread.currentThread().getName(), status);
            return false; // TODO: create log
        }
    }
}
