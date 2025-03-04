package com.crypto.trading.controller;

import com.crypto.trading.model.entity.Transaction;
import com.crypto.trading.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/buy")
    public Transaction buyCrypto(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "cryptoSymbol") String symbol,
                                 @RequestParam(name = "quantity") BigDecimal quantity,
                                 @RequestParam(name = "transactionType") String transactionType) {
        return transactionsService.buyCrypto(email, symbol, quantity, transactionType);
    }

    @PostMapping("/sell")
    public Transaction sellCrypto(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "cryptoSymbol") String symbol,
                                 @RequestParam(name = "quantity") BigDecimal quantity,
                                 @RequestParam(name = "transactionType") String transactionType) {
        return transactionsService.sellCrypto(email, symbol, quantity, transactionType);
    }
}
