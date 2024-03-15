package com.foodmarket.foodmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Food DTO Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodDTO {

    @Schema(description = "name")
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @Schema(description = "description")
    @NotEmpty(message = "description should not be null or empty")
    private String description;

    @Schema(description = "ingredients")
    @NotEmpty(message = "ingredients should not be null or empty")
    private String ingredients;

    @Schema(description = "price")
    @NotNull(message = "price should not be null or empty")
    private Integer price;

    @Schema(description = "rate")
    @NotNull(message = "rate should not be null or empty")
    private Double rate;

    @Schema(description = "types")
    @NotEmpty(message = "types should not be null or empty")
    private String types;
}
