package com.wefox.payment.processor.external.db.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.core.utils.enums.PaymentType;
import com.wefox.payment.processor.external.db.repository.dao.AccountDAO;
import com.wefox.payment.processor.external.db.repository.dao.PaymentDAO;
import com.wefox.payment.processor.external.db.repository.entities.AccountEntity;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JPAAccountRepositoryImplTest {

    @InjectMocks
    private JPAAccountRepositoryImpl jpaAccountRepository;
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private PaymentDAO paymentDAO;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class FindById
    {
        @Test
        @DisplayName("(findById) -> is the happy path working?")
        final void findById() {

            // given
            var account = Account.builder()
                    .id(1L)
                    .payments(new HashSet<>())
                    .build();

            // when
            when(accountDAO.findById(1)).thenReturn(Optional.of(AccountEntity.map(account)));

            // then
            final var optionalPersistedAccount = assertDoesNotThrow(() -> jpaAccountRepository.findById(1));
            assertTrue(optionalPersistedAccount.isPresent());
        }

        @Test
        @DisplayName("(findById) -> is the empty path working?")
        final void findByIdEmpty() {

            // when
            when(accountDAO.findById(1)).thenReturn(Optional.empty());

            // then
            final var optionalPersistedAccount = assertDoesNotThrow(() -> jpaAccountRepository.findById(1));
            assertTrue(optionalPersistedAccount.isEmpty());
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class Save
    {
        @Test
        @DisplayName("(save) -> is the happy path working?")
        final void save() {

            // given
            var payment = Payment.builder()
                    .id(UUID.randomUUID())
                    .type(PaymentType.ONLINE)
                    .build();
            var account = Account.builder()
                    .id(1L)
                    .payments(Stream.of(payment).collect(Collectors.toSet()))
                    .build();

            // when
            when(accountDAO.save(any(AccountEntity.class))).thenReturn(AccountEntity.map(account));

            // then
            assertDoesNotThrow(() -> jpaAccountRepository.save(account));
            verify(paymentDAO, times(1)).saveAll(anyCollection());
        }

    }
}