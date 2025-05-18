package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.CreateUserRequest;
import com.devterin.socialmedia.dtos.request.UpdateUserRequest;
import com.devterin.socialmedia.dtos.response.UserResponse;
import com.devterin.socialmedia.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserRequest request);
    UserResponse toDto(User user);
    void updateUserFromDto(@MappingTarget User user, UpdateUserRequest request);

}
