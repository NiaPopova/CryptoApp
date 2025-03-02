package com.crypto.trading.model.dto;

import com.crypto.trading.model.entity.CryptoCurrency;
import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.entity.UserHoldId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserHoldDTO {
    private UserHoldId id;
    private User user;
    private CryptoCurrency currency;
    private BigDecimal quantity;
}
