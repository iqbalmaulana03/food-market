package com.foodmarket.foodmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "GET Food DTO Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFoodDTO {

    @Schema(description = "name")
    private String name;

    @Schema(description = "price")
    private Integer price;

    @Schema(description = "types")
    private String types;
}
