package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.FoodDTO;
import com.foodmarket.foodmarket.dto.response.FoodResponse;
import com.foodmarket.foodmarket.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoFoodMapper {

    AutoFoodMapper MAPPER = Mappers.getMapper(AutoFoodMapper.class);

    FoodResponse mapToFoodDto(Food food);

    Food mapToFood(FoodDTO foodDTO);
}
