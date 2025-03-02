package com.crypto.trading.service;

import com.crypto.trading.model.entity.User;
import com.crypto.trading.repository.UserHoldRepository;
import com.crypto.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHoldRepository userHoldRepository;

    public User getUserBalance(String email) {
        checkEmail(email);

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NoSuchElementException("There is no user with the email " + email);
        }
    }

    public User resetAccount(String email) {
        checkEmail(email);

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            User user = opt.get();
            user.setBalance(BigDecimal.valueOf(10000));

            userHoldRepository.deleteAllByIdUserId(user.getUserId());
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException("There is no user with the email " + email);
        }
    }

    private void checkEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (email == null || !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email!");
        }
    }
}
