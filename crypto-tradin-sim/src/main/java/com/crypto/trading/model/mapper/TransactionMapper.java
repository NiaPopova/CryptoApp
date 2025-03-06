package com.crypto.trading.model.mapper;

import com.crypto.trading.model.dto.TransactionDTO;
import com.crypto.trading.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "transactionType", source = "type")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "cryptoCurrency", source = "cryptoCurrency")
    TransactionDTO transactionToTransactionDTO(Transaction transaction);
}
