package com.devterin.socialmedia.dtos.request;

import com.devterin.socialmedia.enums.NotifyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
    private NotifyType type;
    private String redirectUrl;
}
