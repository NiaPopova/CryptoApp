package com.crypto.trading.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "CRYPTOCURRENCIES")
@Getter
@Setter
public class CryptoCurrency {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    @Setter(AccessLevel.NONE)
    private Integer cryptoId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SYMBOL")
    private String symbol;
}
