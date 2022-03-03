package com.wefox.payment.processor.external.client.verification.components;

import com.wefox.payment.processor.core.exceptions.GlobalProcessorException;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import com.wefox.payment.processor.external.client.verification.IPaymentVerificator;
import com.wefox.payment.processor.external.client.verification.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verification.model.PaymentDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;

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
        }catch (URISyntaxException | IOException e) {
            log.error("(PaymentVerificatorImpl) -> (validatePayment): There was a timeout for the payment [{}]", payment.getId());
            throw new GlobalProcessorException(LogErrorType.NETWORK, e.getMessage(), payment.getId().toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.fatal("(PaymentVerificatorImpl) -> (validatePayment): The thread [{}] was interrupted", Thread.currentThread().getName());
            throw new GlobalProcessorException(LogErrorType.OTHER, e.getMessage(), payment.getId().toString());
        }
    }
}
