package com.devterin.socialmedia.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyResponse {
    private String senderName;
    private String senderAvatar;
    private String message;
    private String timeDifference;
    private String type;
    private String redirectUrl;
}
