package com.foodmarket.foodmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Transaction DTO Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    @Schema(name = "foodId")
    @NotNull(message = "foodId not null")
    private Long foodId;

    @Schema(name = "quantity")
    @NotNull(message = "quantity not null")
    private Integer quantity;
}
