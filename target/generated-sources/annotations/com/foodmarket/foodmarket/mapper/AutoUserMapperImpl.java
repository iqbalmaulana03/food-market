package com.foodmarket.foodmarket.mapper;

import com.foodmarket.foodmarket.dto.response.UserResponse;
import com.foodmarket.foodmarket.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-15T14:18:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class AutoUserMapperImpl implements AutoUserMapper {

    @Override
    public UserResponse mapToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getName() );
        userResponse.email( user.getEmail() );
        userResponse.address( user.getAddress() );
        userResponse.houseNumber( user.getHouseNumber() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.city( user.getCity() );

        return userResponse.build();
    }
}
