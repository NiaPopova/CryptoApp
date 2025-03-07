package com.crypto.trading.service;

import com.crypto.trading.exception.AssetNotOwnedException;
import com.crypto.trading.exception.NotFoundException;
import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.entity.UserHold;
import com.crypto.trading.repository.UserHoldRepository;
import com.crypto.trading.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserHoldServiceTest {

    @Mock
    UserHoldRepository userHoldRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserHoldService userHoldService;

    @Test
    void getaAllUserHoldsIsSuccessfulTest() {
        User user = new User();
        user.setEmail("user@example.com");

        UserHold userHold1 = new UserHold();
        UserHold userHold2 = new UserHold();
        List<UserHold> userHolds = List.of(userHold1, userHold2);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userHoldRepository.findByIdUserId(user.getUserId())).thenReturn(userHolds);

        List<UserHold> result = userHoldService.getAllUserHolds(user.getEmail());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userHold1, result.get(0));
        assertEquals(userHold2, result.get(1));
    }

    @Test
    void getAllUserHoldsThrowsNotFoundExceptionTest() {
        when(userRepository.findByEmail("user")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userHoldService.getAllUserHolds("user"));
    }

    @Test
    void getAllUserHoldsThrowsAssetNotFoundException() throws IllegalAccessException, NoSuchFieldException {
        User user = new User();
        user.setEmail("user@example.com");

        Field userIdField = User.class.getDeclaredField("userId");
        userIdField.setAccessible(true);
        userIdField.set(user, 1);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userHoldRepository.findByIdUserId(user.getUserId())).thenReturn(null);

        assertThrows(AssetNotOwnedException.class, () -> userHoldService.getAllUserHolds("user@example.com"));
    }
}
