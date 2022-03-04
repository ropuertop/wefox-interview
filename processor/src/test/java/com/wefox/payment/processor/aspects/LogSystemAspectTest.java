package com.wefox.payment.processor.aspects;

import com.wefox.payment.processor.core.exceptions.GlobalProcessorException;
import com.wefox.payment.processor.events.model.PaymentDTO;
import com.wefox.payment.processor.external.client.logs.ILogSystem;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogSystemAspectTest {

    @InjectMocks
    private LogSystemAspect logSystemAspect;
    @Mock
    private ILogSystem logSystem;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class ProcessException
    {
        @Test
        @DisplayName("(processException) -> is the happy path working?")
        final void processException() {

            // given
            var joinPoint = mock(JoinPoint.class);
            var ex = new GlobalProcessorException(LogErrorType.NETWORK, "testing", UUID.randomUUID().toString());

            // when
            when(logSystem.registerErrorLog(anyString(), any(LogErrorType.class), anyString())).thenReturn(Optional.of(LocalDateTime.now()));

            // then
            assertDoesNotThrow(() -> logSystemAspect.processException(joinPoint, ex));
            verify(logSystem, times(1)).registerErrorLog(anyString(), any(LogErrorType.class), anyString());
        }

        @Test
        @DisplayName("(processException) -> is the retry strategy working?")
        final void processExceptionWithRetry() {

            // given
            var joinPoint = mock(JoinPoint.class);
            var ex = new GlobalProcessorException(LogErrorType.NETWORK, "testing", UUID.randomUUID().toString());

            // when
            when(logSystem.registerErrorLog(anyString(), any(LogErrorType.class), anyString())).thenReturn(Optional.empty());

            // then
            assertDoesNotThrow(() -> logSystemAspect.processException(joinPoint, ex));
            verify(logSystem, times(3)).registerErrorLog(anyString(), any(LogErrorType.class), anyString());
        }

        @Test
        @DisplayName("(processException) -> is the unexpected exception path working with paymentDTO object?")
        final void processExceptionUnexpectedException() {

            // given
            var randomId = UUID.randomUUID().toString();
            var joinPoint = mock(JoinPoint.class);
            var ex = new RuntimeException();
            var paymentDTO = PaymentDTO.builder()
                    .paymentId(randomId)
                    .build();

            // when
            when(joinPoint.getArgs()).thenReturn(new Object[]{paymentDTO});
            when(logSystem.registerErrorLog(anyString(), any(LogErrorType.class), anyString())).thenReturn(Optional.of(LocalDateTime.now()));

            // then
            assertDoesNotThrow(() -> logSystemAspect.processException(joinPoint, ex));
            var paymentIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(logSystem, times(1)).registerErrorLog(paymentIdArgumentCaptor.capture(), any(LogErrorType.class), anyString());
            assertEquals(randomId, paymentIdArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("(processException) -> is the unexpected exception path working without paymentDTO object?")
        final void processExceptionUnexpectedExceptionWithoutPayment() {

            // given
            var joinPoint = mock(JoinPoint.class);
            var ex = new RuntimeException();

            // when
            when(joinPoint.getArgs()).thenReturn(new Object[]{});
            when(logSystem.registerErrorLog(any(), any(LogErrorType.class), anyString())).thenReturn(Optional.of(LocalDateTime.now()));

            // then
            assertDoesNotThrow(() -> logSystemAspect.processException(joinPoint, ex));
            var paymentIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(logSystem, times(1)).registerErrorLog(paymentIdArgumentCaptor.capture(), any(LogErrorType.class), anyString());
            assertNull(paymentIdArgumentCaptor.getValue());
        }
    }
}