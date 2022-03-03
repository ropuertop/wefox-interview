package com.wefox.payment.processor.external.client.verificator.components;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
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
    private final ILogSystem logSystem;

    public PaymentVerificatorImpl(IPaymentVerificatorConnection paymentVerificatorConnection, ILogSystem logSystem) {
        this.paymentVerificatorConnection = paymentVerificatorConnection;
        this.logSystem = logSystem;
    }

    @Override
    public final Boolean validatePayment(final Payment payment) {
        try
        {
            return this.paymentVerificatorConnection.checkIfValid(PaymentDTO.map(payment));

        }catch (HttpTimeoutException e) {
            log.error("(PaymentVerificatorImpl) -> (validatePayment): There was a timeout for the payment [{}]", payment.getId());
            logSystem.registerErrorLog(payment, LogErrorType.NETWORK, e.getMessage());
            return false;
        } catch (URISyntaxException | IOException e) {
            logSystem.registerErrorLog(payment, LogErrorType.NETWORK, e.getMessage());
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.fatal("(PaymentVerificatorImpl) -> (validatePayment): The thread [{}] was interrupted", Thread.currentThread().getName());
            logSystem.registerErrorLog(payment, LogErrorType.OTHER, e.getMessage());
            return false;
        }
    }
}
