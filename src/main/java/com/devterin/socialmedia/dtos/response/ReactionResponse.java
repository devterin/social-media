package com.devterin.socialmedia.dtos.response;

import com.devterin.socialmedia.enums.ReactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionResponse {
    private Long id;
    private Long postId;
    private String username;
    private ReactionType type;
    private Integer totalReactions;
}