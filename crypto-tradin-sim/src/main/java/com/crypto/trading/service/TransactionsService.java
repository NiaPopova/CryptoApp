package com.crypto.trading.service;

import com.crypto.trading.exception.AssetNotOwnedException;
import com.crypto.trading.exception.InsufficientBalanceException;
import com.crypto.trading.exception.NotFoundException;
import com.crypto.trading.exception.UnsupportedCryptoException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionsService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    private UserHoldRepository userHoldRepository;

    @Autowired
    private KrakenWebSocketService krakenWebSocketService;

    public Transaction buyCrypto(String email, String symbol,
                                 BigDecimal quantity,
                                 String transactionType) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = optUser.get();

        BigDecimal balance = user.getBalance();

        Optional<CryptoCurrency> optCrypto = cryptoCurrencyRepository.findBySymbol(symbol);
        if (optCrypto.isEmpty()) {
            throw new UnsupportedCryptoException("We don't support sales with this crypto currency!");
        }

        CryptoCurrency crypto = optCrypto.get();
        BigDecimal cryptoPrice = krakenWebSocketService.getLatestPrice(symbol);

        if (balance.compareTo(quantity.multiply(cryptoPrice)) < 0) {
            throw new InsufficientBalanceException("Your balance is not enough to make a transaction");
        }

        BigDecimal amount = quantity.multiply(cryptoPrice);

        user.setBalance(balance.subtract(amount));
        userRepository.save(user);

        UserHold userHold = new UserHold();
        UserHoldId userHoldId = new UserHoldId();
        userHoldId.setUserId(user.getUserId());
        userHoldId.setCryptoId(crypto.getCryptoId());

        Optional<UserHold> optUserHold = userHoldRepository.findById(userHoldId);
        if (optUserHold.isPresent()) {
            userHold = optUserHold.get();
            BigDecimal userQuantity = userHold.getQuantity();
            userHold.setQuantity(userQuantity.add(quantity));
        } else {
            userHold.setUser(user);
            userHold.setCryptoCurrency(crypto);
            userHold.setQuantity(quantity);
            userHold.setId(userHoldId);
        }

        userHold.setPrice(cryptoPrice);
        userHoldRepository.save(userHold);

        return makeTransaction(transactionType, quantity, amount, cryptoPrice, user, crypto);
    }

    public Transaction sellCrypto(String email, String symbol,
                                  BigDecimal quantity,
                                  String transactionType) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        User user = optUser.get();

        BigDecimal balance = user.getBalance();

        Optional<CryptoCurrency> optCrypto = cryptoCurrencyRepository.findBySymbol(symbol);
        if (optCrypto.isEmpty()) {
            throw new UnsupportedCryptoException("We don't support sales with this crypto currency");
        }

        CryptoCurrency crypto = optCrypto.get();

        BigDecimal cryptoPrice = krakenWebSocketService.getLatestPrice(symbol);

        if (balance.compareTo(quantity.multiply(cryptoPrice)) < 0) {
            throw new InsufficientBalanceException("Your balance is not enough to make a transaction");
        }

        BigDecimal amount = quantity.multiply(cryptoPrice);

        UserHoldId userHoldId = new UserHoldId();
        userHoldId.setUserId(user.getUserId());
        userHoldId.setCryptoId(crypto.getCryptoId());

        UserHold userHold;

        Optional<UserHold> optUSerHold = userHoldRepository.findById(userHoldId);
        if (optUSerHold.isPresent()) {
            userHold = optUSerHold.get();

            int compare = userHold.getQuantity().compareTo(quantity);

            if (compare < 0) {
                throw new InsufficientBalanceException("You dont have enough of the crypto currency!");
            } else if (compare == 0) {
                userHoldRepository.deleteAllById(userHoldId);
            } else {
                userHold.setQuantity(userHold.getQuantity().subtract(quantity));
                userHoldRepository.save(userHold);
            }

        } else {
            throw new AssetNotOwnedException("You don't own any cryptocurrency of this type!");
        }

        user.setBalance(balance.add(amount));
        userRepository.save(user);

        return makeTransaction(transactionType, quantity, amount, cryptoPrice, user, crypto);
    }

    private Transaction makeTransaction(String transactionType, BigDecimal quantity, BigDecimal amount,
                                        BigDecimal cryptoPrice, User user, CryptoCurrency crypto) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.fromValue(transactionType));
        transaction.setQuantity(quantity);
        transaction.setAmount(amount);
        transaction.setPrice(cryptoPrice);
        transaction.setUser(user);
        transaction.setCryptoCurrency(crypto);
        transaction.setTime(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
