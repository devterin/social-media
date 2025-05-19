package com.devterin.socialmedia.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long postId;
    private String content;
    private List<String> imageUrls;
    private Integer likeCount;
    private Integer commentCount;
}
