package com.wefox.payment.processor.external.db.utils;

import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;
import com.wefox.payment.processor.external.db.entities.AccountEntity;

/**
 * This interface will provide a method declaration for mapping every entity into the related
 * domain model
 *
 * @param <T> the {@link AbstractProcessorDomainModel} child that we want to map
 */
@FunctionalInterface
public interface IDBMapper<T extends AbstractProcessorDomainModel>{

    /**
     * This method is in charge of mapping the current entity class into the domain one
     *
     * @return the mapped entity class filled with the domain model passed by parameter
     * @see AccountEntity#map()
     */
    T map();
}
