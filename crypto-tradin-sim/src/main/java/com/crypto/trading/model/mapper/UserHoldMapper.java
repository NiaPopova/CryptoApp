package com.crypto.trading.model.mapper;

import com.crypto.trading.model.dto.UserHoldDTO;
import com.crypto.trading.model.entity.UserHold;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHoldMapper {
    @Mapping(target = "id.userId", source = "id.userId")
    @Mapping(target = "id.cryptoId", source = "id.cryptoId")
    @Mapping(target = "currency.name", source = "cryptoCurrency.name")
    @Mapping(target = "currency.symbol", source = "cryptoCurrency.symbol")
    @Mapping(target = "quantity", source = "quantity")
    UserHoldDTO userHoldToDto(UserHold user);
}
