package com.foodmarket.foodmarket.service;

import com.foodmarket.foodmarket.dto.FoodDTO;
import com.foodmarket.foodmarket.dto.SearchFoodDTO;
import com.foodmarket.foodmarket.dto.response.FoodResponse;
import com.foodmarket.foodmarket.dto.response.ListFoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FoodService {

    ListFoodResponse getAllFood(String name, String types, Integer price);

    Page<FoodResponse> search(SearchFoodDTO request);

    FoodResponse create(FoodDTO foodDTO);

    FoodResponse upload(MultipartFile foodImage, Long foodId) throws IOException;

    FoodResponse update(FoodResponse foodResponse);

    void delete(Long foodId);
}
