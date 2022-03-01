package com.wefox.payment.processor.api.listener.types;

import com.wefox.payment.processor.api.listener.IPaymentsConsumer;
import com.wefox.payment.processor.api.model.types.OnlinePaymentDTO;

/**
 * This interface is in charge of the {@link IPaymentsConsumer} implementation for the online kafka topic
 *
 * @author ropuertop
 */
public interface IOnlineConsumer extends IPaymentsConsumer<OnlinePaymentDTO> {

    String ONLINE_LISTENER = "onlinePayments";
}
