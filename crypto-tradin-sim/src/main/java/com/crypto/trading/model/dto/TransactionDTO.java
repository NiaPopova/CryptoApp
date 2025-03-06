package com.crypto.trading.model.dto;

import com.crypto.trading.model.entity.CryptoCurrency;
import com.crypto.trading.model.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private TransactionType transactionType;
    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private LocalDateTime time;
    private UserDTO user;
    private CryptoCurrency cryptoCurrency;
}
