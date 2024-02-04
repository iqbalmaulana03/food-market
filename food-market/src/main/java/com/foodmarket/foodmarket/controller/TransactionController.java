package com.foodmarket.foodmarket.controller;

import com.foodmarket.foodmarket.dto.TransactionDTO;
import com.foodmarket.foodmarket.dto.response.ListTransaction;
import com.foodmarket.foodmarket.dto.response.TransactionResponse;
import com.foodmarket.foodmarket.service.TransactionService;
import com.midtrans.httpclient.error.MidtransError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CheckOut,Get All  Transaction REST API for Transaction Resource",
        description = "CheckOut,Get All  Transaction Rest APIs - CheckOut,Get All  Transaction"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Operation(
            summary = "GET All Transaction REST API",
            description = "GET All Transaction REST API is used to get a all the transactions from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build get all user User REST API
    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ListTransaction> getUserLimit(){
        ListTransaction all = transactionService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Create or Register Transaction API",
            description = "Create Transaction REST API is used save transaction in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATE"
    )
    @PostMapping("/")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws MidtransError {
        TransactionResponse response = transactionService.checkOut(transactionDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get By Transaction Id Transaction API",
            description = "Get By Id Transaction Transaction REST API is used egt transaction in a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<ListTransaction> getByIdTransaction(@PathVariable("id")
                                                              Long id){
        ListTransaction findById = transactionService.getByID(id);
        return new ResponseEntity<>(findById, HttpStatus.OK);
    }
}
