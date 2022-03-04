package com.wefox.payment.processor.aspects;

import com.wefox.payment.processor.core.exceptions.GlobalProcessorException;
import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import com.wefox.payment.processor.core.model.Payment;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import com.wefox.payment.processor.external.client.logs.components.LogSystemImpl;

/**
 * This aspect is in charge of providing the logic to register every exception thrown
 * on the {@link ILogSystem}.
 *
 * @author ropuertop
 */
@Log4j2
@Aspect
@Service
@EnableAspectJAutoProxy
public class LogSystemAspect {

    /**
     * The {@link ILogSystem} implementation
     *
     * @see LogSystemImpl
     */
    private final ILogSystem logSystem;

    @Autowired
    public LogSystemAspect(final ILogSystem logSystem) {
        this.logSystem = logSystem;
    }

    /**
     * This aspect will be in charge of registering every exception on log system
     *
     * @param jp the {@link JoinPoint} of the signature method
     * @param ex the {@link Throwable} occurred
     */
    @AfterThrowing(pointcut = "@annotation(com.wefox.payment.processor.aspects.LogSystem)", throwing = "ex")
    public void processException(JoinPoint jp, Throwable ex) {

        // checking if the exception is controlled
        if(ex.getClass() == GlobalProcessorException.class)
        {

            // casting the exception received
            final var verificationClientException = ((GlobalProcessorException) ex);

            // registering the error in log system
            this.registerLogWithRetry(
                    verificationClientException.getPaymentId(),
                    verificationClientException.getCode(),
                    verificationClientException.getMessage()
            );

            return;
        }

        // if the exception received is not a GlobalProcessorException,
        // we will treat it as unexpected
        this.processUnexpectedException(jp, ex);
    }

    /**
     * This method is in charge of processing the unexpected exception received
     *
     * @param jp the {@link JoinPoint} associated to the method
     * @param ex the {@link Throwable} received
     */
    private void processUnexpectedException(final JoinPoint jp, final Throwable ex)
    {
        // getting the optional payment identifier
        final var optPaymentId = Arrays.stream(jp.getArgs())
                .filter(arg -> arg.getClass() == PaymentDTO.class)
                .map(payment -> ((PaymentDTO) payment).getPaymentId())
                .findAny();

        // registering a new error in log system
        this.registerLogWithRetry(
                optPaymentId.orElse(null),
                LogErrorType.OTHER,
                String.format("There was an unexpected error processing the payment: %s", ex.getLocalizedMessage())
        );

    }

    /**
     * This method is in charge of retry the call to the log system if there was an error
     * on its communication
     *
     * @param paymentId the {@link Payment} identifier
     * @param errorType the {@link LogErrorType} associated to the error
     * @param message the message associated to the error
     */
    private void registerLogWithRetry(final String paymentId, final LogErrorType errorType, final String message)
    {
        Optional<LocalDateTime> optCreatedAt = Optional.empty();
        int maxAttempt = 0;

        while (maxAttempt < 3 && optCreatedAt.isEmpty())
        {
            // incrementing the
            maxAttempt++;

            // registering the new error log
            optCreatedAt = this.logSystem.registerErrorLog(paymentId, errorType, message);
        }

        optCreatedAt.ifPresentOrElse(createdAt -> log.warn("(LogSystemAspect) -> (processException): registering new error associated to [{}]", paymentId),
                () -> log.fatal("(LogSystemAspect) -> (processException): could not register the error associated to [{}]", paymentId));
    }
}
