package com.foodmarket.foodmarket.controller;

import com.foodmarket.foodmarket.dto.UpdateUserDTO;
import com.foodmarket.foodmarket.dto.response.ListUserResponse;
import com.foodmarket.foodmarket.dto.response.UploadResponse;
import com.foodmarket.foodmarket.dto.response.UserResponse;
import com.foodmarket.foodmarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(
        name = "Update,Get All, Upload Profile  User REST API for Users Resource",
        description = "Update,Get All, Upload Profile User Rest APIs - Update,Get All, Upload Profile User"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    @Operation(
            summary = "UPDATE User REST API",
            description = "Update User REST API is used to update a particular user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build update User REST API
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId,
                                                   @RequestBody @Valid UpdateUserDTO userDto){
        userDto.setId(userId);
        UserResponse updated = userService.updateUser(userDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(
            summary = "GET By Id User REST API",
            description = "GET By Id User REST API is used to get a all the users from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build get all user User REST API
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User', 'Admin')")
    public ResponseEntity<UserResponse> getUserAll(@PathVariable("id") Long id){
        UserResponse user = userService.getUserId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "GET All User REST API",
            description = "GET All User REST API is used to get a all the users from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build get all user User REST API
    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ListUserResponse> getUserLimit(){
        ListUserResponse userAll = userService.getAllLimit();
        return new ResponseEntity<>(userAll, HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE User REST API",
            description = "DELETE User REST API is used to delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    //build delete User REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.delete(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    @Operation(
            description = "Upload endpoint for User",
            summary = "This is a summary for User Upload endpoint",
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
    @PatchMapping(value = "/{userId}/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('User')")
    public UploadResponse uploadAvatar(
            @PathVariable Long userId,
            @RequestPart("avatarFileName") MultipartFile avatarFileName) throws IOException {

        return ResponseEntity.ok(userService.upload(avatarFileName, userId)).getBody();
    }
}
