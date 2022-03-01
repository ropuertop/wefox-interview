package com.wefox.payment.processor.external.db.utils;

import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;

@FunctionalInterface
public interface IDBMapper<T extends AbstractProcessorDomainModel>{

    /**
     * This method is in charge of mapping the current entity class into the domain one
     *
     * @return the mapped entity class filled with the domain model passed by parameter
     */
    T map();
}
