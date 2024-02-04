package com.foodmarket.foodmarket.controller;

import com.foodmarket.foodmarket.dto.AuthDTO;
import com.foodmarket.foodmarket.dto.UserDTO;
import com.foodmarket.foodmarket.dto.response.AuthResponse;
import com.foodmarket.foodmarket.dto.response.UserResponse;
import com.foodmarket.foodmarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Register, Login User REST API for Users Resource",
        description = "Create, Login User Rest APIs - Create User, Login User"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private UserService userService;

    @Operation(
            summary = "Create or Register User API",
            description = "Create User REST API is used save user in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATE"
    )
    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @Operation(
            summary = "Login User API",
            description = "Login User REST API is used login user in a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(userService.login(authDTO));
    }
}
