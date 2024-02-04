package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.TransactionResponse;
import com.foodmarket.foodmarket.entity.Transaction;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-15T14:18:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class AutoTransactionMapperImpl implements AutoTransactionMapper {

    @Override
    public TransactionResponse mapToTransactionDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setId( transaction.getId() );
        transactionResponse.setQuantity( transaction.getQuantity() );
        transactionResponse.setTotal( transaction.getTotal() );
        transactionResponse.setStatus( transaction.getStatus() );
        transactionResponse.setPaymentURL( transaction.getPaymentURL() );

        return transactionResponse;
    }
}
