package com.crypto.trading.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Setter(AccessLevel.NONE)
    private Integer transactionId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "TIME")
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CRYPTO_ID", nullable = false)
    private CryptoCurrency cryptoCurrency;
}
