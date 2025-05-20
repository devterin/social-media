package com.devterin.socialmedia.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCommentRequest {
    private Long postId;
    private String description;
    private String username;
}
