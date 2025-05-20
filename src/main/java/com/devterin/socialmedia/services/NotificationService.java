package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.NotifyRequest;
import com.devterin.socialmedia.dtos.response.NotifyResponse;
import com.devterin.socialmedia.entities.Notification;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.NotifyMapper;
import com.devterin.socialmedia.repositories.NotificationRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotifyMapper notifyMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(NotifyRequest request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Notification notification = notifyMapper.toEntity(request);
        notification.setSender(sender);
        notification.setReceiver(receiver);

        Notification savedNotify = notificationRepository.save(notification);
        NotifyResponse response = notifyMapper.toDto(savedNotify);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/socket/notification", response
        );
    }
}
