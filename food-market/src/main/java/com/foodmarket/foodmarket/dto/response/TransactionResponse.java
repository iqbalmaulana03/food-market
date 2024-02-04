package com.foodmarket.foodmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private Integer quantity;
    private Integer total;
    private String status;
    private String paymentURL;
    private Long userId;
    private Long foodId;
}
