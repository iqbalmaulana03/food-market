package com.foodmarket.foodmarket.service;

import com.foodmarket.foodmarket.dto.AuthDTO;
import com.foodmarket.foodmarket.dto.UpdateUserDTO;
import com.foodmarket.foodmarket.dto.UserDTO;
import com.foodmarket.foodmarket.dto.response.AuthResponse;
import com.foodmarket.foodmarket.dto.response.ListUserResponse;
import com.foodmarket.foodmarket.dto.response.UploadResponse;
import com.foodmarket.foodmarket.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserResponse createUser(UserDTO userDTO);

    AuthResponse login(AuthDTO authDTO);

    UserResponse updateUser(UpdateUserDTO updateUserDTO);

    UserResponse getUserId(Long id);

    ListUserResponse getAllLimit();

    UploadResponse upload(MultipartFile avatarFileName, Long id) throws IOException;

    void delete(Long id);
}
