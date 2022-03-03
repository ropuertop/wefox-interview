package com.wefox.payment.processor.core.exceptions;

import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import lombok.Getter;

@Getter
public class GlobalProcessorException extends RuntimeException{
    private final LogErrorType code;
    private final String message;
    private final String paymentId;

    public GlobalProcessorException(final LogErrorType code,
                                    final String message,
                                    final String paymentId) {
        this.code = code;
        this.message = message;
        this.paymentId = paymentId;
    }
}
