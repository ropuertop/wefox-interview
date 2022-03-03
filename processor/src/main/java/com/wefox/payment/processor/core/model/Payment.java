package com.wefox.payment.processor.core.model;

import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.wefox.payment.processor.ProcessorApplication;

/**
 * This {@link Payment} class will represent the payments that the {@link ProcessorApplication} will process
 *
 * @author ropuertop
 */
@Getter
@Builder
public class Payment extends AbstractProcessorDomainModel {

    private final UUID id;

    private final PaymentType type;

//    @CreditCardNumber(message = "The credit card number must has a valid format")
    private final String creditCard;

    @Positive(message = "The payment amount must be positive")
    private final BigDecimal amount;

    @PastOrPresent(message = "Invalid payment creation date")
    private final LocalDateTime createdAt;

    private Account account;
}
