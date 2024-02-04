package com.foodmarket.foodmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User DTO Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDTO {

    @Schema(description = "name")
    @NotNull(message = "id should not be null or empty")
    private Long id;

    @Schema(description = "name")
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @Schema(description = "email")
    @NotEmpty(message = "email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String email;

    @Schema(description = "password")
    @NotEmpty(message = "password should not be null or empty")
    private String password;

    @Schema(description = "address")
    @NotEmpty(message = "address should not be null or empty")
    private String address;

    @Schema(description = "houseNumber")
    @NotEmpty(message = "houseNumber should not be null or empty")
    private String houseNumber;

    @Schema(description = "phoneNumber")
    @NotEmpty(message = "phoneNumber should not be null or empty")
    private String phoneNumber;

    @Schema(description = "city")
    @NotEmpty(message = "city should not be null or empty")
    private String city;
}
