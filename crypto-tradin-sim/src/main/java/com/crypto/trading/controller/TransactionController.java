package com.crypto.trading.controller;

import com.crypto.trading.utils.SessionManager;
import com.crypto.trading.model.dto.TransactionDTO;
import com.crypto.trading.model.mapper.TransactionMapper;
import com.crypto.trading.service.TransactionsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping("/allTransactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestParam(name = "email") String email,
                                                                   HttpServletRequest request) {
        SessionManager.validateLogin(request, email);
        return ResponseEntity.ok(transactionsService.getAllUserTransactions(email).stream()
            .map(transactionMapper::transactionToTransactionDTO).toList());
    }

    @PostMapping("/buy")
    public ResponseEntity<TransactionDTO> buyCrypto(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "cryptoSymbol") String symbol,
                                                    @RequestParam(name = "quantity") BigDecimal quantity,
                                                    @RequestParam(name = "transactionType") String transactionType,
                                                    HttpServletRequest request) {
        SessionManager.validateLogin(request, email);
        return ResponseEntity.ok(transactionMapper.transactionToTransactionDTO(
            transactionsService.buyCrypto(email, symbol, quantity, transactionType)));
    }

    @PostMapping("/sell")
    public ResponseEntity<TransactionDTO> sellCrypto(@RequestParam(name = "email") String email,
                                                     @RequestParam(name = "cryptoSymbol") String symbol,
                                                     @RequestParam(name = "quantity") BigDecimal quantity,
                                                     @RequestParam(name = "transactionType") String transactionType,
                                                     HttpServletRequest request) {
        SessionManager.validateLogin(request, email);
        return ResponseEntity.ok(transactionMapper.transactionToTransactionDTO(
            transactionsService.sellCrypto(email, symbol, quantity, transactionType)));
    }
}
