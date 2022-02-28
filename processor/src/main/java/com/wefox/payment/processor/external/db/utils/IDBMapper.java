package com.wefox.payment.processor.external.db.utils;

import com.wefox.payment.processor.core.model.global.AbstractProcessorDomainModel;

public interface IDBMapper<T extends AbstractProcessorDomainModel, E>{

    /**
     * This method is in charge of mapping the current domain class into the entity one
     *
     * @param domainModel the domain model that we want to map
     * @return the mapped entity class filled with the domain model passed by parameter
     */
    E map(T domainModel);
}
