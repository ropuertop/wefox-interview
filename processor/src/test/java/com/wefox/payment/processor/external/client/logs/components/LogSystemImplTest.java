package com.wefox.payment.processor.external.client.logs.components;

import com.wefox.payment.processor.external.client.logs.connection.ILogSystemConnection;
import com.wefox.payment.processor.external.client.logs.model.request.ErrorModelDTORequest;
import com.wefox.payment.processor.external.client.logs.model.response.ErrorModelDTOResponse;
import com.wefox.payment.processor.external.client.logs.utils.LogErrorType;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogSystemImplTest {

    @InjectMocks
    private LogSystemImpl logSystem;
    @Mock
    private ILogSystemConnection logSystemConnection;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class RegisterErrorLog
    {
        @Test
        @DisplayName("(registerErrorLog) -> is the happy path working?")
        final void registerErrorLog() throws Exception {
            // given
            var paymentId = UUID.randomUUID().toString();
            var errorType = LogErrorType.NETWORK;
            var message = "testing";
            var logSystemResponse = mock(ErrorModelDTOResponse.class);

            // when
            when(logSystemResponse.getCreatedAt()).thenReturn(LocalDateTime.now());
            when(logSystemConnection.registerNewError(any(ErrorModelDTORequest.class))).thenReturn(logSystemResponse);

            // then
            final var optCreatedAt = assertDoesNotThrow(() -> logSystem.registerErrorLog(paymentId, errorType, message));
            assertTrue(optCreatedAt.isPresent());
        }

        @Test
        @DisplayName("(registerErrorLog) -> is the comm exceptions path working?")
        final void registerErrorLogWithCommProblems() throws Exception {
            // given
            var paymentId = UUID.randomUUID().toString();
            var errorType = LogErrorType.NETWORK;
            var message = "testing";

            // when
            doThrow(IOException.class).when(logSystemConnection).registerNewError(any(ErrorModelDTORequest.class));

            // then
            final var optCreatedAt = assertDoesNotThrow(() -> logSystem.registerErrorLog(paymentId, errorType, message));
            assertTrue(optCreatedAt.isEmpty());
        }

        @Test
        @DisplayName("(registerErrorLog) -> is the interrupt exceptions path working?")
        final void registerErrorLogWithThreadInterruptProblems() throws Exception {
            // given
            var paymentId = UUID.randomUUID().toString();
            var errorType = LogErrorType.NETWORK;
            var message = "testing";

            // when
            doThrow(InterruptedException.class).when(logSystemConnection).registerNewError(any(ErrorModelDTORequest.class));

            // then
            final var optCreatedAt = assertDoesNotThrow(() -> logSystem.registerErrorLog(paymentId, errorType, message));
            assertTrue(optCreatedAt.isEmpty());
        }
    }

}
