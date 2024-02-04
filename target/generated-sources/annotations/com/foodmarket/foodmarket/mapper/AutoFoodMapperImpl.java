package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.FoodDTO;
import com.foodmarket.foodmarket.dto.response.FoodResponse;
import com.foodmarket.foodmarket.entity.Food;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-15T14:18:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class AutoFoodMapperImpl implements AutoFoodMapper {

    @Override
    public FoodResponse mapToFoodDto(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodResponse.FoodResponseBuilder foodResponse = FoodResponse.builder();

        foodResponse.id( food.getId() );
        foodResponse.name( food.getName() );
        foodResponse.description( food.getDescription() );
        foodResponse.ingredients( food.getIngredients() );
        foodResponse.price( food.getPrice() );
        foodResponse.rate( food.getRate() );
        foodResponse.types( food.getTypes() );
        foodResponse.picturePath( food.getPicturePath() );

        return foodResponse.build();
    }

    @Override
    public Food mapToFood(FoodDTO foodDTO) {
        if ( foodDTO == null ) {
            return null;
        }

        Food food = new Food();

        food.setName( foodDTO.getName() );
        food.setDescription( foodDTO.getDescription() );
        food.setIngredients( foodDTO.getIngredients() );
        food.setPrice( foodDTO.getPrice() );
        food.setRate( foodDTO.getRate() );
        food.setTypes( foodDTO.getTypes() );

        return food;
    }
}
