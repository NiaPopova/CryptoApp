package com.crypto.trading.controller;

import com.crypto.trading.model.dto.UserHoldDTO;
import com.crypto.trading.model.mapper.UserHoldMapper;
import com.crypto.trading.service.UserHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/hold")
public class UserHoldController {

    @Autowired
    private UserHoldService userHoldService;

    @Autowired
    private UserHoldMapper userHoldMapper;

    @GetMapping("/transactions")
    public ResponseEntity<List<UserHoldDTO>> getUserHolds(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(
            userHoldService.getAllTransactionsByEmail(email).stream().map(userHoldMapper::userHoldToDto).collect(
                Collectors.toList()));
    }
}
