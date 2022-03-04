package com.wefox.payment.processor.external.client.verification.components;

import com.wefox.payment.processor.core.exceptions.GlobalProcessorException;
import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.external.client.verification.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verification.model.PaymentDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentVerificatorImplTest {

    @InjectMocks
    private PaymentVerificatorImpl paymentVerificator;
    @Mock
    private IPaymentVerificatorConnection paymentVerificatorConnection;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class ValidatePayment
    {
        @Test
        @DisplayName("(validatePayment) -> is the happy path working?")
        final void validatePayment() throws Exception
        {
            // given
            var account = mock(Account.class);
            var payment = Payment.builder()
                    .id(UUID.randomUUID())
                    .account(account)
                    .type(PaymentType.ONLINE)
                    .amount(BigDecimal.TEN)
                    .build();

            // when
            when(account.getId()).thenReturn(1L);
            when(paymentVerificatorConnection.checkIfValid(any(PaymentDTO.class))).thenReturn(true);

            // then
            final var result = assertDoesNotThrow(() -> paymentVerificator.validatePayment(payment));
            assertTrue(result);
        }

        @Test
        @DisplayName("(validatePayment) -> is the comm exceptions path working?")
        final void validatePaymentWithCommProblems() throws Exception
        {
            // given
            var randomPaymentId = UUID.randomUUID();
            var account = mock(Account.class);
            var payment = Payment.builder()
                    .id(randomPaymentId)
                    .account(account)
                    .type(PaymentType.ONLINE)
                    .amount(BigDecimal.TEN)
                    .build();

            // when
            when(account.getId()).thenReturn(1L);
            doThrow(IOException.class).when(paymentVerificatorConnection).checkIfValid(any(PaymentDTO.class));

            // then
            final var exception = assertThrows(GlobalProcessorException.class, () -> paymentVerificator.validatePayment(payment));
            assertEquals(randomPaymentId.toString(), exception.getPaymentId());
        }

        @Test
        @DisplayName("(validatePayment) -> is the thread interrupt exceptions path working?")
        final void validatePaymentWithInterruptProblems() throws Exception
        {
            // given
            var randomPaymentId = UUID.randomUUID();
            var account = mock(Account.class);
            var payment = Payment.builder()
                    .id(randomPaymentId)
                    .account(account)
                    .type(PaymentType.ONLINE)
                    .amount(BigDecimal.TEN)
                    .build();

            // when
            when(account.getId()).thenReturn(1L);
            doThrow(InterruptedException.class).when(paymentVerificatorConnection).checkIfValid(any(PaymentDTO.class));

            // then
            final var exception = assertThrows(GlobalProcessorException.class, () -> paymentVerificator.validatePayment(payment));
            assertEquals(randomPaymentId.toString(), exception.getPaymentId());
        }
    }
}