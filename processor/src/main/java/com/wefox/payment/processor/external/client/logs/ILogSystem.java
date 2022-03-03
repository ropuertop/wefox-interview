package com.wefox.payment.processor.external.client.logs;

import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.logs.model.response.ErrorModelDTOResponse;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ILogSystem {

    /**
     * This method is in charge of storing any {@link Payment} processing error
     * @param payment that suffered the error
     * @param errorType the {@link LogErrorType}
     * @param errorMessage a description related with the error
     * @return a new {@link ErrorModelDTOResponse} provided by the log system
     */
    Optional<LocalDateTime> registerErrorLog(final String paymentId, final LogErrorType errorType, final String errorMessage);
}
