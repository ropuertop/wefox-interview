package com.wefox.payment.processor.core.service.components;

import com.wefox.payment.processor.core.model.Account;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.db.IAccountRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfflineAccountServiceImplTest {

    @InjectMocks
    private OfflineAccountServiceImpl offlineAccountService;
    @Mock
    private IAccountRepository accountRepository;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class AddNewPayments
    {
        @Test
        @DisplayName("(addNewPayments) -> is the happy path working?")
        final void addNewPayments() {

            // given
            var account = Account.builder().id(1L).payments(new HashSet<>()).build();
            var payment = mock(Payment.class);
            var mockAccount = mock(Account.class);

            // when
            when(accountRepository.save(account)).thenReturn(mockAccount);
            when(mockAccount.getId()).thenReturn(1L);

            // then
            final var persistedAccount = assertDoesNotThrow(() -> offlineAccountService.addNewPayments(account, payment));
            verify(accountRepository, times(1)).save(account);
            assertEquals(account.getId(), persistedAccount.getId());
        }

        @Test
        @DisplayName("(addNewPayments) -> is the method throwing (shouldn't it) if the payment is null?")
        final void addNewPaymentsWithNullPayment() {

            // given
            var account = Account.builder().id(1L).payments(new HashSet<>()).build();
            var mockAccount = mock(Account.class);

            // when
            when(accountRepository.save(account)).thenReturn(mockAccount);
            when(mockAccount.getId()).thenReturn(1L);

            // then
            final var persistedAccount = assertDoesNotThrow(() -> offlineAccountService.addNewPayments(account, (Payment) null));
            verify(accountRepository, times(1)).save(account);
            assertEquals(account.getId(), persistedAccount.getId());
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class GetAccount
    {
        @Test
        @DisplayName("(getAccount) -> is the happy path working?")
        final void getAccount() {

            // given
            var accountId = 1;
            var account = mock(Account.class);

            // when
            when(accountRepository.findById(1)).thenReturn(Optional.of(account));
            when(account.getId()).thenReturn(1L);

            // then
            final var optionalPersistedAccount = assertDoesNotThrow(() -> offlineAccountService.getAccount(accountId));
            assertTrue(optionalPersistedAccount.isPresent());
            assertEquals(1L, optionalPersistedAccount.get().getId());
        }

        @Test
        @DisplayName("(getAccount) -> is the empty path working?")
        final void getAccountEmptyValue() {

            // given
            var accountId = 1;

            // when
            when(accountRepository.findById(1)).thenReturn(Optional.empty());

            // then
            final var optionalPersistedAccount = assertDoesNotThrow(() -> offlineAccountService.getAccount(accountId));
            assertTrue(optionalPersistedAccount.isEmpty());
        }
    }
}