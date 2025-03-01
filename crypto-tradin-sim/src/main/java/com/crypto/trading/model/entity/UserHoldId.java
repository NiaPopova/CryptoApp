package com.crypto.trading.model.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserHoldId implements Serializable {
    private Integer userId;
    private Integer cryptoId;

    //todo
}
