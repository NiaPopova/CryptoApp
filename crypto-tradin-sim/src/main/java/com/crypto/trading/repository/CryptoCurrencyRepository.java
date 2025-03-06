package com.crypto.trading.repository;

import com.crypto.trading.model.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Integer> {
    Optional<CryptoCurrency> findBySymbol(String symbol);
}
