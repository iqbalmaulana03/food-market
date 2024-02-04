package com.foodmarket.foodmarket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchFoodDTO {

    private String name;

    private String types;

    private Integer minPrice;

    private Integer maxPrice;

    @NotNull
    private Integer size;

    @NotNull
    private Integer page;
}
