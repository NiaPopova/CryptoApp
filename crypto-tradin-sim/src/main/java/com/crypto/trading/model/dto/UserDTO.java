package com.crypto.trading.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDTO {
    private String email;
    private BigDecimal balance;
}
