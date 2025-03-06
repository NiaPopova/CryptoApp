package com.crypto.trading.controller;

import com.crypto.trading.service.KrakenWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crypto")
public class CryptoCurrencyController {

    @Autowired
    private KrakenWebSocketService krakenWebSocketService;

    @GetMapping("/top-20")
    public ResponseEntity<Map<String, BigDecimal>> getTop20() {
        return ResponseEntity.ok(krakenWebSocketService.getCryptoPrices()
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .limit(20)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
