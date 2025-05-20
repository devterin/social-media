package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.NotifyRequest;
import com.devterin.socialmedia.dtos.response.NotifyResponse;
import com.devterin.socialmedia.entities.Notification;
import com.devterin.socialmedia.utils.AppConstants;
import com.devterin.socialmedia.utils.DateTimeUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class NotifyMapper {

    public Notification toEntity(NotifyRequest request) {

        return Notification.builder()
                .message(request.getMessage())
                .type(request.getType())
                .redirectUrl(request.getRedirectUrl())
                .isRead(false)
                .build();
    }

    public NotifyResponse toDto(Notification notification) {

        return NotifyResponse.builder()
                .message(notification.getMessage())
                .redirectUrl(notification.getRedirectUrl())
                .senderAvatar(notification.getSender().getAvatar() != null
                        ? notification.getSender().getAvatar().getImageUrl() : AppConstants.URL_DEFAULT_AVATAR)
                .senderName(notification.getSender().getUsername())
                .type(notification.getType().name())
                .timeDifference(DateTimeUtil.getTimeDifference(notification.getCreatedAt()))
                .build();
    }


}
