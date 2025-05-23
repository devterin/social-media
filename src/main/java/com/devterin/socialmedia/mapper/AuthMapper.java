package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.LoginRequest;
import com.devterin.socialmedia.dtos.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    LoginResponse toDto(String accessToken, String refreshToken);
}
