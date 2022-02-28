package com.wefox.payment.processor.core.model.global;

import com.wefox.payment.processor.core.utils.functions.IValidator;

import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Set;

/**
 * This class is in charge of declaring the global behavior of the domain models.
 * Every model must extend from this class.
 *
 * @author ropuertop
 */
public abstract class AbstractProcessorDomainModel implements IValidator {

    /**
     * Save every {@link ConstraintViolation} associated with the {@link AbstractProcessorDomainModel}
     */
    private Set<ConstraintViolation<AbstractProcessorDomainModel>> constraintViolations;

    @Override
    public final Boolean isValid() {

        // initializing the ConstraintViolation of the class instance
        if(Objects.isNull(this.constraintViolations))
        {
            this.constraintViolations = VALIDATOR.validate(this);
        }

        return this.constraintViolations.isEmpty();
    }
}
