package com.wefox.payment.processor.events.functions;

import com.wefox.payment.processor.events.IPaymentProcessor;
import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.core.service.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This class is in charge of consume every message of kafka online stream
 *
 * @author ropuertop
 */
@Log4j2
@Service
@DependsOn("processor/online/service")
public class OnlineConsumerService {


    private final IPaymentProcessor paymentProcessor;

    /**
     * Parameterized constructor with the current {@link IAccountService} bean implementation
     *
     * @param paymentProcessor the {@link IAccountService} implementation
     */
    @Autowired
    public OnlineConsumerService(
            @Qualifier("processor/online") final IPaymentProcessor paymentProcessor
    ) {
        this.paymentProcessor = paymentProcessor;
    }


    @Bean
    public Consumer<PaymentDTO> onlinePayment() {
        return this.paymentProcessor::processPayment;
    }
}
