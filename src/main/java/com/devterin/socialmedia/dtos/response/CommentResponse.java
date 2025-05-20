package com.devterin.socialmedia.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long postId;
    private String username;
    private String description;
    private String avatarUser;
    private LocalDateTime createdAt;
}
