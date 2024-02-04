package com.foodmarket.foodmarket.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {

    @JsonIgnore
    private Long id;

    private String name;

    private String description;

    private String ingredients;

    private Integer price;

    private Double rate;

    private String types;

    private String picturePath;
}
