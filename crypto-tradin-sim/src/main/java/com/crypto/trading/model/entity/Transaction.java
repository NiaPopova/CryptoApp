package com.crypto.trading.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer transactionId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CRYPTO_ID", nullable = false)
    private CryptoCurrency cryptoCurrency;
}
