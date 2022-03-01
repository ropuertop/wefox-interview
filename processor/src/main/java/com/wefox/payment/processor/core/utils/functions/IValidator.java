package com.wefox.payment.processor.core.utils.functions;

import javax.validation.Validation;
import javax.validation.Validator;
import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;

/**
 * This interface will provide a method definition and a {@link Validator} to check if the instance
 * is valid or not
 *
 * @author ropuertop
 */
@FunctionalInterface
public interface IValidator{

    /**
     * The default {@link Validator} got on {@link javax.validation.ValidatorFactory}
     */
    Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Check if the instance that implements this interface is valid.
     *
     * @return TRUE if the instance is valid
     * @implNote this interface will be implemented by {@link AbstractProcessorDomainModel}
     */
    Boolean isValid();
}
