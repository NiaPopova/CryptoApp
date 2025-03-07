package com.crypto.trading.service;

import com.crypto.trading.exception.NotFoundException;
import com.crypto.trading.model.entity.CryptoCurrency;
import com.crypto.trading.model.entity.Transaction;
import com.crypto.trading.model.entity.TransactionType;
import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.entity.UserHold;
import com.crypto.trading.model.entity.UserHoldId;
import com.crypto.trading.repository.CryptoCurrencyRepository;
import com.crypto.trading.repository.TransactionRepository;
import com.crypto.trading.repository.UserHoldRepository;
import com.crypto.trading.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Mock
    UserHoldRepository userHoldRepository;

    @Mock
    KrakenWebSocketService krakenWebSocketService;

    @InjectMocks
    TransactionsService transactionsService;

    @Test
    void getAllUserTransactionsIsSuccessful() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setEmail("user@example.com");

        Field userIdField = User.class.getDeclaredField("userId");
        userIdField.setAccessible(true);
        userIdField.set(user, 1);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> transactions = List.of(transaction1, transaction2);

        when(transactionRepository.findAllByUserUserId(user.getUserId())).thenReturn(transactions);
        List<Transaction> result = transactionsService.getAllUserTransactions(user.getEmail());

        assertEquals(transactions, result);
    }

    @Test
    void getAllUserTransactionsThrowsNotFoundException() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionsService.getAllUserTransactions("user@example.com"));
    }

    @Test
    void buyCryptoIsSuccessful() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setEmail("user@example.com");
        user.setBalance(BigDecimal.valueOf(20000));

        String symbol = "BTC";
        BigDecimal quantity = BigDecimal.valueOf(1);
        String transactionType = "BUY";
        BigDecimal price = BigDecimal.valueOf(1000);
        BigDecimal amount = quantity.multiply(price);

        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol(symbol);
        cryptoCurrency.setName("Bitcoin");

        UserHold userHold = new UserHold();
        UserHoldId userHoldId = new UserHoldId();

        Field cryptoId = CryptoCurrency.class.getDeclaredField("cryptoId");
        cryptoId.setAccessible(true);
        cryptoId.set(cryptoCurrency, 1);

        userHoldId.setUserId(user.getUserId());
        userHoldId.setCryptoId(cryptoCurrency.getCryptoId());
        userHold.setQuantity(quantity);
        userHold.setCryptoCurrency(cryptoCurrency);
        userHold.setUser(user);
        userHold.setPrice(price);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cryptoCurrencyRepository.findBySymbol(symbol)).thenReturn(Optional.of(cryptoCurrency));
        when(krakenWebSocketService.getLatestPrice(symbol)).thenReturn(price);
        when(userRepository.save(user)).thenReturn(user);
        when(userHoldRepository.findById(userHoldId)).thenReturn(Optional.empty());
        when(userHoldRepository.save(userHold)).thenReturn(userHold);

        Transaction transaction = new Transaction();
        transaction.setTime(LocalDateTime.now());
        transaction.setType(TransactionType.fromValue(transactionType));
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setCryptoCurrency(cryptoCurrency);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);

        assertEquals(transaction, transactionsService.buyCrypto(user.getEmail(), symbol, quantity, transactionType));
    }
}
