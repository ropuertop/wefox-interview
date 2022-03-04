package com.wefox.payment.processor.events.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.service.IAccountService;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.events.model.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentProcessorImplTest {

    @InjectMocks
    private PaymentProcessorImpl paymentProcessor;
    @Mock
    private IAccountService accountService;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("(processPayment) -> is the happy path working?")
    final void processPayment() {
        // given
        var uuid = UUID.randomUUID();
        var account = mock(Account.class);
        var paymentDto = PaymentDTO.builder()
                .accountId(1)
                .paymentId(uuid.toString())
                .paymentType(PaymentType.OFFLINE.name().toLowerCase())
                .createdAt(LocalDateTime.now())
                .creditCard("creditCard")
                .amount(BigDecimal.TEN.intValue())
                .build();

        // when
        when(accountService.getAccount(1)).thenReturn(Optional.of(account));
        when(accountService.addNewPayments(any(Account.class), any(Payment.class))).thenReturn(account);

        // then
        assertDoesNotThrow(() -> paymentProcessor.processPayment(paymentDto));
    }
}