package com.devterin.socialmedia.dtos.request;

import com.devterin.socialmedia.enums.ReactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionRequest {
    private Long postId;
    private ReactionType type;
}