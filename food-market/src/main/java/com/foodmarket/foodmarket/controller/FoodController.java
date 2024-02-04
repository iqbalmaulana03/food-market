package com.foodmarket.foodmarket.controller;

import com.foodmarket.foodmarket.dto.FoodDTO;
import com.foodmarket.foodmarket.dto.SearchFoodDTO;
import com.foodmarket.foodmarket.dto.response.FoodResponse;
import com.foodmarket.foodmarket.dto.response.ListFoodResponse;
import com.foodmarket.foodmarket.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(
        name = "Create Food REST API for Foods Resource",
        description = "Create Food Rest APIs - Create Food"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/foods")
public class FoodController {

    private FoodService foodService;

    @Operation(
            summary = "GET All Foods REST API",
            description = "GET All Foods REST API is used to get a all the foods from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/getFood")
    public ResponseEntity<ListFoodResponse> getUserAll(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "types", required = false) String types,
                                                       @RequestParam(value = "price", required = false) Integer price){
        ListFoodResponse all = foodService.getAllFood(name, types, price);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "GET Food All REST API",
            description = "GET Food All REST API is used to get a single food from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/search/")
    public ResponseEntity<List<FoodResponse>> search(@RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "types", required = false) String types,
                                                     @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                     @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        SearchFoodDTO request = SearchFoodDTO.builder()
                .name(name)
                .types(types)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .build();

        Page<FoodResponse> search = foodService.search(request);

        return new ResponseEntity<>(search.getContent(), HttpStatus.OK);
    }

    @Operation(
            summary = "Create or Register Food API",
            description = "Create Food REST API is used save food in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATE"
    )
    @PostMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<FoodResponse> createFood(@Valid @RequestBody FoodDTO foodDTO){
        FoodResponse response = foodService.create(foodDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            description = "Upload endpoint for Upload Campaign",
            summary = "This is a summary for Campaign upload endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @PatchMapping(value = "/{foodId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<FoodResponse> upload(
            @PathVariable Long foodId,
            @RequestPart("foodImage") MultipartFile foodImage) throws IOException {
        FoodResponse response = foodService.upload(foodImage, foodId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE Foods REST API",
            description = "Update Food REST API is used to update a particular food in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build update User REST API
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<FoodResponse> updateUser(@PathVariable("id") Long foodId,
                                                   @RequestBody @Valid FoodResponse foodResponse){
        foodResponse.setId(foodId);
        FoodResponse updated = foodService.update(foodResponse);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE Foods REST API",
            description = "DELETE Foods REST API is used to delete a particular foods from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build delete User REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long fooId){
        foodService.delete(fooId);
        return new ResponseEntity<>("Food successfully deleted!", HttpStatus.OK);
    }
}
