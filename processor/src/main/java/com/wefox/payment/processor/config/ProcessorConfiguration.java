package com.wefox.payment.processor.config;

import com.wefox.payment.processor.api.listener.types.IOfflineConsumer;
import com.wefox.payment.processor.api.listener.types.IOnlineConsumer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({IOnlineConsumer.class, IOfflineConsumer.class})
public final class ProcessorConfiguration {


}
