package com.wefox.payment.processor.external.client.logs.components;

import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.connection.ILogSystemConnection;
import com.wefox.payment.processor.external.client.logs.model.request.ErrorModelDTORequest;
import com.wefox.payment.processor.external.client.logs.model.response.ErrorModelDTOResponse;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
public class LogSystemImpl implements ILogSystem {

    private final ILogSystemConnection logSystemConnection;

    public LogSystemImpl(final ILogSystemConnection logSystemConnection) {
        this.logSystemConnection = logSystemConnection;
    }

    @Override
    public final Optional<LocalDateTime> registerErrorLog(final String paymentId, final LogErrorType errorType, final String errorMessage) {

        // building the request dto
        final var requestErrorDTO = ErrorModelDTORequest.builder()
                .paymentId(paymentId)
                .errorText(errorType.name().toLowerCase())
                .errorDescription(errorMessage)
                .build();

        try {
            return Optional.ofNullable(logSystemConnection.registerNewError(requestErrorDTO)).map(ErrorModelDTOResponse::getCreatedAt);
        } catch (IOException | URISyntaxException e) {
            log.error("(LogSystemImpl) -> (registerErrorLog): there was a problem [{}] trying to register the error [{}] associated with [{}]", e, errorType, paymentId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.fatal("(LogSystemImpl) -> (registerErrorLog): The thread [{}] was interrupted", Thread.currentThread().getName());
        }

        return Optional.empty();
    }
}
