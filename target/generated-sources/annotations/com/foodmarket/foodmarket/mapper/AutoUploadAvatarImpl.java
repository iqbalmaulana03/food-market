package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.UploadResponse;
import com.foodmarket.foodmarket.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-15T14:18:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class AutoUploadAvatarImpl implements AutoUploadAvatar {

    @Override
    public UploadResponse mapToUpload(User user) {
        if ( user == null ) {
            return null;
        }

        UploadResponse uploadResponse = new UploadResponse();

        uploadResponse.setAvatarFileName( user.getAvatarFileName() );

        return uploadResponse;
    }
}
