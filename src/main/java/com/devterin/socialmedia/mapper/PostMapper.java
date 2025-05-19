package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.CreatePostRequest;
import com.devterin.socialmedia.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(CreatePostRequest request);




}
