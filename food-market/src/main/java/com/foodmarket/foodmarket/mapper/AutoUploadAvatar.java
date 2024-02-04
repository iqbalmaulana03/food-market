package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.UploadResponse;
import com.foodmarket.foodmarket.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUploadAvatar {

    AutoUploadAvatar MAPPER = Mappers.getMapper(AutoUploadAvatar.class);

    UploadResponse mapToUpload(User user);
}
