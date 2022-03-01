package com.wefox.payment.processor.core.model;

import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.wefox.payment.processor.ProcessorApplication;

/**
 * This {@link Account} domain model will be the aggregate root for the {@link ProcessorApplication}.
 *
 * @author ropuertop
 */
@Getter
@Builder
public class Account extends AbstractProcessorDomainModel {

    @Positive
    private final Long id;

    @NotBlank(message = "The account name must be valid")
    @Length(max = 150, message = "The account name cannot be greater than 150 characters")
    private String name;

    @Email(message = "The email must has a valid format")
    private String email;

    @PastOrPresent(message = "Invalid account creation date")
    private final LocalDateTime createdAt;

    @PastOrPresent(message = "Invalid account birth date")
    private final LocalDateTime birthDate;

    private Set<Payment> payments;

    /**
     * This method is in charge of updating the list of {@link Payment} associated
     * to the {@link Account}
     * @param payment the new {@link Payment} received
     */
    public Account addNewPayments(final Payment... payment)
    {
        this.payments.addAll(payments.stream().filter(Payment::isValid).collect(Collectors.toSet()));
        return this;
    }

    /**
     * This method is in charge of retrieving the last {@link Payment} date
     * @return a {@link LocalDateTime} with its last payment date
     */
    public Optional<LocalDateTime> findLastPaymentDate()
    {
        return this.payments.parallelStream()
                .min(Comparator.comparing(Payment::getCreatedAt))
                .map(Payment::getCreatedAt);
    }
}
