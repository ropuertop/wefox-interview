package com.wefox.payment.processor.api.listener.types;

import com.wefox.payment.processor.api.listener.IPaymentsConsumer;
import com.wefox.payment.processor.api.model.types.OfflinePaymentDTO;

/**
 * This interface is in charge of the {@link IPaymentsConsumer} implementation for the offline kafka topic.
 *
 * @author ropuertop
 */
public interface IOfflineConsumer extends IPaymentsConsumer<OfflinePaymentDTO> {

    String OFFLINE_LISTENER = "offlinePayments";
}
