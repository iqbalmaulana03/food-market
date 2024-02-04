package com.foodmarket.foodmarket.service;

import com.foodmarket.foodmarket.dto.TransactionDTO;
import com.foodmarket.foodmarket.dto.response.ListTransaction;
import com.foodmarket.foodmarket.dto.response.TransactionResponse;
import com.midtrans.httpclient.error.MidtransError;

public interface TransactionService {

    TransactionResponse checkOut(TransactionDTO transactionDTO) throws MidtransError;

    ListTransaction getAll();

    ListTransaction getByID(Long transactionId);
}
