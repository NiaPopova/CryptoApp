package com.crypto.trading.controller;

import com.crypto.trading.SessionManager;
import com.crypto.trading.model.dto.UserHoldDTO;
import com.crypto.trading.model.mapper.UserHoldMapper;
import com.crypto.trading.service.UserHoldService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<UserHoldDTO>> getUserHolds(@RequestBody String email,
                                                          HttpServletRequest request) {
        SessionManager.validateLogin(request);
        return ResponseEntity.ok(
            userHoldService.getAllTransactionsByEmail(email).stream().map(userHoldMapper::userHoldToDto).collect(
                Collectors.toList()));
    }
}
