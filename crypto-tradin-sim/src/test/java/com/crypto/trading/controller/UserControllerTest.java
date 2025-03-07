package com.crypto.trading.controller;

import com.crypto.trading.model.dto.UserDTO;
import com.crypto.trading.model.entity.User;
import com.crypto.trading.model.mapper.UserMapper;
import com.crypto.trading.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    UserMapper userMapper;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    UserController userController;

    private MockMvc mockMvc;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setEmail("user@example.com");
        user.setBalance(new BigDecimal(1000));

        userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setBalance(user.getBalance());
    }

    @Test
    void loginIsSuccessfulTest() throws Exception {

        when(userService.login(user.getEmail())).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        mockMvc.perform(post("/user/account/login")
                .param("email", user.getEmail()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.balance").value(user.getBalance()));
    }

    @Test
    void getUserBalanceIsSuccessful() throws Exception {
        when(userService.getUserBalance(user.getEmail())).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        mockMvc.perform(get("/user/account/balance")
            .param("email", user.getEmail()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.balance").value(user.getBalance()));
    }
}