package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.UserResponse;
import com.foodmarket.foodmarket.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);

    UserResponse mapToUserDto(User user);
}
