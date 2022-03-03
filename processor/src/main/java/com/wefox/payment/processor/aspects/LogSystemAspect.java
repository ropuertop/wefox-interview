package com.wefox.payment.processor.aspects;

import com.wefox.payment.processor.core.exceptions.GlobalProcessorException;
import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Log4j2
@Aspect
@Service
@EnableAspectJAutoProxy
public class LogSystemAspect {

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

        if(ex.getClass() == GlobalProcessorException.class)
        {

            // casting the exception received
            final var verificationClientException = ((GlobalProcessorException) ex);

            // registering the new error log
            final var optCreatedAt = this.logSystem.registerErrorLog(
                    verificationClientException.getPaymentId(),
                    verificationClientException.getCode(),
                    verificationClientException.getMessage()
            );

            optCreatedAt.ifPresentOrElse(createdAt -> log.warn("(LogSystemAspect) -> (processException): registering new error associated to [{}]", verificationClientException.getPaymentId()),
                    () -> log.fatal("(LogSystemAspect) -> (processException): could not register the error associated to [{}]", verificationClientException.getPaymentId()));

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
                .filter(arg -> arg.getClass().isAssignableFrom(PaymentDTO.class))
                .map(payment -> ((PaymentDTO) payment).getPaymentId())
                .findAny();

        // registering a new error log in log system
        final var optCreatedAt = this.logSystem.registerErrorLog(
                optPaymentId.orElse(null),
                LogErrorType.OTHER,
                String.format("There was an unexpected error processing the payment: %s", ex.getLocalizedMessage())
        );

        optCreatedAt.ifPresentOrElse(createdAt -> log.warn("(LogSystemAspect) -> (processException): registering new error associated to [{}]", optPaymentId.orElse(null)),
                () -> log.fatal("(LogSystemAspect) -> (processException): could not register the error associated to [{}]", optPaymentId.orElse(null)));
    }
}
