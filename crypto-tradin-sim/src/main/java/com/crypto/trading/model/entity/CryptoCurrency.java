package com.crypto.trading.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "CRYPTOCURRENCIES")
public class CryptoCurrency {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer cryptoId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "PRICE")
    private BigDecimal price;
}
