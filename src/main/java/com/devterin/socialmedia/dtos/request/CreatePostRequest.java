package com.devterin.socialmedia.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CreatePostRequest {
    private Long userId;
    private String content;
    private List<String> imageUrls;
}
