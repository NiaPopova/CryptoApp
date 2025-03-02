package com.crypto.trading.model.mapper;

import com.crypto.trading.model.dto.UserDTO;
import com.crypto.trading.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "balance", source = "balance")
    UserDTO userToUserDTO(User user);
}
