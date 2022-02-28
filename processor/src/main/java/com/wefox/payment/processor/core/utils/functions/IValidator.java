package com.wefox.payment.processor.core.utils.functions;

import javax.validation.Validation;
import javax.validation.Validator;

@FunctionalInterface
public interface IValidator{

    Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    Boolean isValid();
}
