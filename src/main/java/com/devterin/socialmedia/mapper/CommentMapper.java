package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.AddCommentRequest;
import com.devterin.socialmedia.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(AddCommentRequest request);

}
