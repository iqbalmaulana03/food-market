package com.foodmarket.foodmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Auth DTO Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {

    @Schema(description = "email")
    @NotEmpty(message = "email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String email;

    @Schema(description = "password")
    @NotEmpty(message = "password should not be null or empty")
    private String password;
}
