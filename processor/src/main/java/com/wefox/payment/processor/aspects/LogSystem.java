package com.wefox.payment.processor.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.wefox.payment.processor.external.client.logs.ILogSystem;

/**
 * This annotation is in charge of getting every exception thrown and
 * register it on {@link ILogSystem}
 *
 * @author ropuertop
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface LogSystem {
}
