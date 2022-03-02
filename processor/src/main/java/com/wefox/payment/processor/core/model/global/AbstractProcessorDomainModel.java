package com.wefox.payment.processor.core.model.global;

import com.wefox.payment.processor.core.utils.functions.IValidator;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Set;

/**
 * This class is in charge of declaring the global behavior of the domain models.
 * Every domain model must extend from this class.
 *
 * @author ropuertop
 */
@Log4j2
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


        if(Boolean.FALSE.equals(this.constraintViolations.isEmpty()))
        {
            log.warn("Invalid [{}]. It has the next errors [{}]", this.getClass().getName(), this.constraintViolations);
        }

        return this.constraintViolations.isEmpty();
    }
}
