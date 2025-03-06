package com.crypto.trading.controller;

import com.crypto.trading.utils.SessionManager;
import com.crypto.trading.model.dto.UserDTO;
import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.mapper.UserMapper;
import com.crypto.trading.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/account")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam(name = "email") String email, HttpServletRequest request) {
        User user = userService.login(email);
        request.getSession().setAttribute(SessionManager.LOGGED, true);
        request.getSession().setAttribute(SessionManager.LOGGED_FROM, request.getRemoteAddr());
        request.getSession().setAttribute(SessionManager.USER_ID, user.getEmail());

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @GetMapping("/balance")
    public ResponseEntity<UserDTO> getUserBalance(@RequestParam(name = "email") String email,
                                                  HttpServletRequest request) {
        SessionManager.validateLogin(request, email);
        return ResponseEntity.ok(userMapper.userToUserDTO(userService.getUserBalance(email)));
    }

    @PostMapping("/reset")
    public ResponseEntity<UserDTO> resetAccount(@RequestParam(name = "email") String email,
                                                HttpServletRequest request) {
        SessionManager.validateLogin(request, email);
        return ResponseEntity.ok(userMapper.userToUserDTO(userService.resetAccount(email)));
    }
}
