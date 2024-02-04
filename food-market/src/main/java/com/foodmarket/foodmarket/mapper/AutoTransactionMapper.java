package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.TransactionResponse;
import com.foodmarket.foodmarket.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoTransactionMapper {

    AutoTransactionMapper MAPPER = Mappers.getMapper(AutoTransactionMapper.class);

    TransactionResponse mapToTransactionDto(Transaction transaction);
}
