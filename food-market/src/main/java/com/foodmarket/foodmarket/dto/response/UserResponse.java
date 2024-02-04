package com.foodmarket.foodmarket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "email")
    private String email;

    @Schema(description = "address")
    private String address;

    @Schema(description = "houseNumber")
    private String houseNumber;

    @Schema(description = "phoneNumber")
    private String phoneNumber;

    @Schema(description = "city")
    private String city;
}
