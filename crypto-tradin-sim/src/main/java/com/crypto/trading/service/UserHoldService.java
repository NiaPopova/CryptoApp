package com.crypto.trading.service;

import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.entity.UserHold;
import com.crypto.trading.repository.UserHoldRepository;
import com.crypto.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserHoldService {

    @Autowired
    private UserHoldRepository userHoldRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserHold> getAllTransactionsByEmail(String email) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            List<UserHold> holds = userHoldRepository.findByIdUserId(opt.get().getUserId());
            if (holds.isEmpty()) {
                throw new NoSuchElementException("The user does not have any crypto-holdings");
            }
            return holds;
        } else {
            throw new NoSuchElementException("There is no user with the email " + email);
        }
    }

}
