package com.foodmarket.foodmarket.serviceImpl;

import com.foodmarket.foodmarket.Enum.Role;
import com.foodmarket.foodmarket.config.JwtService;
import com.foodmarket.foodmarket.dto.AuthDTO;
import com.foodmarket.foodmarket.dto.UpdateUserDTO;
import com.foodmarket.foodmarket.dto.UserDTO;
import com.foodmarket.foodmarket.dto.response.AuthResponse;
import com.foodmarket.foodmarket.dto.response.ListUserResponse;
import com.foodmarket.foodmarket.dto.response.UploadResponse;
import com.foodmarket.foodmarket.dto.response.UserResponse;
import com.foodmarket.foodmarket.entity.User;
import com.foodmarket.foodmarket.exception.BadRequestException;
import com.foodmarket.foodmarket.exception.EmailAlreadyExistsException;
import com.foodmarket.foodmarket.exception.ResourceNotFoundException;
import com.foodmarket.foodmarket.mapper.AutoUploadAvatar;
import com.foodmarket.foodmarket.mapper.AutoUserMapper;
import com.foodmarket.foodmarket.repository.UserRepository;
import com.foodmarket.foodmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.upload-file-path}")
    private String uploadPath;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for User");
        }

        var user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .address(userDTO.getAddress())
                .houseNumber(userDTO.getHouseNumber())
                .phoneNumber(userDTO.getPhoneNumber())
                .city(userDTO.getCity())
                .created_time(new Date())
                .role(Role.User)
                .build();
        userRepository.save(user);

        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public AuthResponse login(AuthDTO authDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getEmail(),
                        authDTO.getPassword()
                )
        );

        var user = userRepository.findByEmail(authDTO.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public UserResponse updateUser(UpdateUserDTO updateUserDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long loggedInUserId = null;

        if (authentication != null && authentication.getPrincipal() instanceof User userPrincipal){
            loggedInUserId = userPrincipal.getId();
        }

        if (loggedInUserId == null || !loggedInUserId.equals(updateUserDTO.getId())) {
            throw new BadRequestException("User", "user", loggedInUserId);
        }

        User existingUser = userRepository.findById(updateUserDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "user", updateUserDTO.getId())
        );
        existingUser.setName(updateUserDTO.getName());
        existingUser.setEmail(updateUserDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        existingUser.setAddress(updateUserDTO.getAddress());
        existingUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
        existingUser.setHouseNumber(updateUserDTO.getHouseNumber());
        existingUser.setCity(updateUserDTO.getCity());
        existingUser.setUpdated_time(new Date());

        User updated = userRepository.save(existingUser);

        return AutoUserMapper.MAPPER.mapToUserDto(updated);
    }

    @Override
    public UserResponse getUserId(Long id) {
        User byId = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "user", id)
        );

        return AutoUserMapper.MAPPER.mapToUserDto(byId);
    }

    @Override
    public ListUserResponse getAllLimit() {

        List<Object[]> limit = userRepository.getUserLimit();
        List<UserResponse> list = new ArrayList<>();

        for (Object[] food : limit) {
            UserResponse responses = new UserResponse();
            responses.setId((Long) food[0]);
            responses.setName((String) food[1]);
            responses.setEmail((String) food[2]);
            responses.setAddress((String) food[3]);
            responses.setHouseNumber((String) food[4]);
            responses.setPhoneNumber((String) food[5]);
            responses.setCity((String) food[6]);
            list.add(responses);
        }

        ListUserResponse response = new ListUserResponse();
        response.setResponse(list);
        return response;
    }

    @Override
    public UploadResponse upload(MultipartFile avatarFileName, Long id) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long loggedInUserId = null;

        if (authentication != null && authentication.getPrincipal() instanceof User userPrincipal){
            loggedInUserId = userPrincipal.getId();
        }

        if (loggedInUserId == null || !loggedInUserId.equals(id)) {
            throw new BadRequestException("User", "user", loggedInUserId);
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "user", id)
        );

        String filename = user.getId() + "_" + avatarFileName.getOriginalFilename();
        Path filePath = Path.of(uploadPath, filename);

        Files.copy(avatarFileName.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        user.setAvatarFileName(filename);
        user.setUpdated_time(new Date());

        User uploadFile = userRepository.save(user);

        return AutoUploadAvatar.MAPPER.mapToUpload(uploadFile);
    }

    @Override
    public void delete(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "userId", id)
        );
        userRepository.deleteById(existingUser.getId());
    }
}
