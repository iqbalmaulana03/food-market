package com.foodmarket.foodmarket.serviceImpl;

import com.foodmarket.foodmarket.dto.FoodDTO;
import com.foodmarket.foodmarket.dto.SearchFoodDTO;
import com.foodmarket.foodmarket.dto.response.FoodResponse;
import com.foodmarket.foodmarket.dto.response.ListFoodResponse;
import com.foodmarket.foodmarket.entity.Food;
import com.foodmarket.foodmarket.exception.ResourceNotFoundException;
import com.foodmarket.foodmarket.mapper.AutoFoodMapper;
import com.foodmarket.foodmarket.repository.FoodRepository;
import com.foodmarket.foodmarket.service.FoodService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

    private FoodRepository foodRepository;

    @Override
    public ListFoodResponse getAllFood(String name, String types, Integer price) {
        List<Object[]> all = foodRepository.getDataFood(name, types, price);
        List<FoodResponse> list = new ArrayList<>();

        for (Object[] food : all) {
            FoodResponse responses = new FoodResponse();
            responses.setId((Long) food[0]);
            responses.setName((String) food[1]);
            responses.setDescription((String) food[2]);
            responses.setPicturePath((String) food[3]);
            responses.setPrice((Integer) food[4]);
            responses.setRate((Double) food[5]);
            responses.setTypes((String) food[6]);
            list.add(responses);
        }

        ListFoodResponse response = new ListFoodResponse();
        response.setResponse(list);
        return response;
    }

    @Override
    public Page<FoodResponse> search(SearchFoodDTO request) {

        Specification<Food> specification =((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
            }

            if (Objects.nonNull(request.getTypes())){
                predicates.add(criteriaBuilder.like(root.get("types"), "%"+request.getTypes()+"%"));
            }

            if (Objects.nonNull(request.getMinPrice())){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }

            if (Objects.nonNull(request.getMaxPrice())){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            return query.where(predicates.toArray(new  Predicate[]{})).getRestriction();
        });

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Food> all = foodRepository.findAll(specification, pageable);
        List<FoodResponse> responses = all.getContent().stream()
                .map(this::toFoodResponse)
                .toList();

        return new PageImpl<>(responses, pageable, all.getTotalElements());
    }

    @Override
    public FoodResponse create(FoodDTO foodDTO) {
        Food food = AutoFoodMapper.MAPPER.mapToFood(foodDTO);
        food.setCreated_time(new Date());

        Food save = foodRepository.save(food);

        return AutoFoodMapper.MAPPER.mapToFoodDto(save);
    }

    @Override
    public FoodResponse upload(MultipartFile foodImage, Long foodId) throws IOException {
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new ResourceNotFoundException("Food", "food", foodId)
        );

        String uploadPath = "food-market/src/main/upload/";
        String fileName = "food/" + food.getId() + "-" + foodImage.getOriginalFilename();
        Path filePath = Path.of(uploadPath, fileName);

        Files.copy(foodImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        food.setPicturePath(fileName);
        food.setUpdated_time(new Date());

        var image = foodRepository.save(food);
        return AutoFoodMapper.MAPPER.mapToFoodDto(image);
    }

    @Override
    public FoodResponse update(FoodResponse foodResponse) {
        Food food = foodRepository.findById(foodResponse.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Food", "food", foodResponse.getId())
        );

        food.setName(foodResponse.getName());
        food.setDescription(foodResponse.getDescription());
        food.setIngredients(foodResponse.getIngredients());
        food.setPrice(foodResponse.getPrice());
        food.setRate(foodResponse.getRate());
        food.setTypes(foodResponse.getTypes());
        food.setUpdated_time(new Date());

        foodRepository.save(food);

        return AutoFoodMapper.MAPPER.mapToFoodDto(food);
    }

    @Override
    public void delete(Long foodId) {
        Food delete = foodRepository.findById(foodId).orElseThrow(
                () ->  new ResourceNotFoundException("FoodId", "foodId", foodId)
        );

        foodRepository.deleteById(delete.getId());
    }

    private FoodResponse toFoodResponse(Food food){
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .ingredients(food.getIngredients())
                .price(food.getPrice())
                .rate(food.getRate())
                .types(food.getTypes())
                .picturePath(food.getPicturePath())
                .build();
    }
}
