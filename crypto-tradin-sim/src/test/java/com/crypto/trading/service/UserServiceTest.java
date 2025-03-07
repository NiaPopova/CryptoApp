package com.crypto.trading.service;

import com.crypto.trading.model.entity.User;
import com.crypto.trading.repository.UserHoldRepository;
import com.crypto.trading.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    UserHoldRepository userHoldRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getUserBalanceIsSuccessfulTest() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setBalance(BigDecimal.valueOf(1000));

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserBalance("user@example.com");

        assertEquals(user.getBalance(), result.getBalance());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUserBalanceUserDoesntExistTest() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserBalance("user@example.com"));
    }

    @Test
    void resetAccountIsSuccessfulTest() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setBalance(BigDecimal.valueOf(20000));

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.resetAccount("user@example.com");

        assertEquals(BigDecimal.valueOf(10000),result.getBalance());
        verify(userHoldRepository, times(1)).deleteAllByIdUserId(user.getUserId());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void resetAccountUserDoesntExistTest() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.resetAccount("user@example.com"));
    }


    @Test
    void loginIsSuccessfulTest() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setBalance(BigDecimal.valueOf(20000));

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        User result = userService.login("user@example.com");

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(BigDecimal.valueOf(20000), result.getBalance());
    }

    @Test
    void loginUserDoesntExistTest() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.login("user@example.com"));
    }

    @Test
    void loginUserThrowsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> userService.login(null));
        assertThrows(IllegalArgumentException.class, () -> userService.login("user"));
    }
}
