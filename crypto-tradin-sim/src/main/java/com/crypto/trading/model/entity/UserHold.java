package com.crypto.trading.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "USER_HOLDS")
@Getter
@Setter
public class UserHold {
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private UserHoldId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @MapsId("cryptoId")
    @JoinColumn(name = "CRYPTO_ID")
    private CryptoCurrency cryptoCurrency;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

}
