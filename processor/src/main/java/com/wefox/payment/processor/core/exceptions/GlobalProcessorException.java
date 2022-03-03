package com.wefox.payment.processor.core.exceptions;

import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import lombok.Getter;
import com.wefox.payment.processor.core.model.Payment;

/**
 * This exception will gather every domain, third party or db exception captured
 * on the fly
 *
 * @author ropuertop
 */
@Getter
public class GlobalProcessorException extends RuntimeException{
    private final LogErrorType code;
    private final String message;
    private final String paymentId;

    /**
     * Parameterized constructor with every attribute data
     *
     * @param code the {@link LogErrorType} associated
     * @param message a short description of the error
     * @param paymentId the {@link Payment} identifier
     */
    public GlobalProcessorException(final LogErrorType code,
                                    final String message,
                                    final String paymentId) {
        this.code = code;
        this.message = message;
        this.paymentId = paymentId;
    }
}
