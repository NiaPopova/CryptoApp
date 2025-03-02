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
@Table(name = "USERS")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    @Setter(AccessLevel.NONE)
    private Integer userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BALANCE")
    private BigDecimal balance;
}
